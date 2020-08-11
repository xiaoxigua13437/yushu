package com.zhaofang.yushu.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhaofang.yushu.common.LoggerUtils;
import com.zhaofang.yushu.dto.ApiAccessLog;
import com.zhaofang.yushu.service.ApiAccessLogService;
//import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * 统一日志处理切面
 *
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect implements ApplicationContextAware, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    //用于存储日志
    public final static ThreadLocal<ApiAccessLog> threadLocal = new ThreadLocal<ApiAccessLog>();

    //录入消息队列,用于异步处理
    public final static BlockingDeque<ApiAccessLog> objQueue = new LinkedBlockingDeque<>(10000);

    //
    public final static ThreadLocal<Map<String,Object>> threadMap = new ThreadLocal<>();

    //安排在给定的延迟后处理
    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    //创建对应的ApplicationContext
    static ApplicationContext context;


    /**
     * @Description: 方法调用前触发, 第一调用
     * @param joinPoint
     * @throws Throwable
     */
    @Before("execution(public * com.zhaofang.yushu.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();//记录方法开始执行的时间
        //获取当前请求对象,该类会暴露与线程绑定的RequestAttributes对象，什么意思呢？ 就是说web请求过来的数据可以跟线程绑定， 用户A，用户B分别请求过来，可以使用RequestContextHolder得到各个请求的数据
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取请求的sessionId
        String sessionId = request.getRequestedSessionId();
        //获取请求路径
        String url = request.getRequestURI();
        ApiAccessLog apiAccessLog = new ApiAccessLog();//记录当前请求信息
        //获取请求参数
        String paramData = JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue);

        apiAccessLog.setReqTime(System.currentTimeMillis());
        //设置客户端ip
        apiAccessLog.setReqClientIp(LoggerUtils.getCliectIp(request));
        apiAccessLog.setReqUserAgent(LoggerUtils.getClientUserAgent(request));
        //设置请求方法
        apiAccessLog.setReqMethod(request.getMethod());
        //设置请求类型（json|普通请求）
        apiAccessLog.setReqType(LoggerUtils.getRequestType(request));
        //设置请求参数内容json字符串
        apiAccessLog.setReqData(paramData);
        //设置请求url
        apiAccessLog.setReqUri(url);
        LOGGER.info(JSON.toJSONString(apiAccessLog).toString());
        threadLocal.set(apiAccessLog);

    }

    /**
     * 环绕触发,第二次调用
     * @param pjp
     * @return
     */
    @Around("execution(public * com.zhaofang.yushu.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp){
        Object result = null;

        try {
            result = pjp.proceed();
            ApiAccessLog logInfo = threadLocal.get();
            if (logInfo == null){
                //donothing
            }else {
                /***
                 * 如果超过1024，则只截取1024的长度。
                 */
                logInfo.setRepData(result.toString().length() > 1024 ? result.toString().substring(1024)
                        : result.toString());
            }

        } catch (Throwable e) {
            LOGGER.error("doAround error:" + e.getMessage(), e);
        }
        return result;
    }

    @After("execution(public * com.zhaofang.yushu.controller.*.*(..))")
    public void doAfter(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();

        ApiAccessLog loggerInfo = threadLocal.get();
        //获取请求错误码
        int status = response.getStatus();
        long endTime = System.currentTimeMillis();//记录方法执行完成的时间
        //设置返回时间
        loggerInfo.setRepTime(endTime);
        //设置请求时间差
        loggerInfo.setCostTime(Integer.valueOf((endTime - loggerInfo.getReqTime()) + ""));
        //设置返回错误码
        loggerInfo.setRqpHttpStatusCode(status + "");
        //加入消息队列,异步消费
        objQueue.add(loggerInfo);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(loggerInfo.getRepData());
        }
        threadLocal.remove();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        context = applicationContext;

        /**
         * 每5秒执行一次
         */
        scheduledExecutorService.scheduleAtFixedRate(new WebLogAspect.LoggerInfoComsumer(
                context.getBean(ApiAccessLogService.class)), 10, 5,
                TimeUnit.SECONDS);

    }

    /**
     * 负责从队列里消费
     */
    private static class LoggerInfoComsumer implements Runnable{

        private ApiAccessLogService loggerService;

        private long lastReportTime;
        private long consumeredSize;
        private long maxCount = 500;
        private long reportMin = 5;

        public LoggerInfoComsumer(ApiAccessLogService s) {
            this.loggerService = s;
        }

        @Override
        public void run() {

            int size = objQueue.size();
            if (size > 0){
                List<ApiAccessLog> lst = new ArrayList<>();
                while (size > 0){
                    lst.add(objQueue.poll());
                    size--;
                }
                loggerService.bathSave(lst);
                consumeredSize += lst.size();

                //定时打印日志
                if (consumeredSize > maxCount || (System.currentTimeMillis() - lastReportTime > reportMin * 60 * 1000L)){
                    LOGGER.info("access log comsumed:" + lst.size());
                    lastReportTime = System.currentTimeMillis();
                    consumeredSize = 0;
                }else {
                    if ((System.currentTimeMillis() - lastReportTime) > reportMin * 60 * 1000L) {
                        LOGGER.info("access log comsumed:" + consumeredSize);
                        lastReportTime = System.currentTimeMillis();
                        consumeredSize = 0;
                    }
                }
            }
        }
    }


    /**
     * 钩子
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        scheduledExecutorService.shutdown();
    }






//    @Around("webLog()")
//    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();//记录方法开始执行的时间
//        //获取当前请求对象
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        //获取请求的sessionId
//        String sessionId = request.getRequestedSessionId();
//
//        //记录请求信息
//        WebLog webLog = new WebLog();
//
//        Object result = joinPoint.proceed();
//
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        if (method.isAnnotationPresent(ApiOperation.class)) {
//            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
//            webLog.setDescription(apiOperation.value());
//        }
//        long endTime = System.currentTimeMillis();
//        String urlStr = request.getRequestURL().toString();
//        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
//        webLog.setIp(request.getRemoteUser());
//        webLog.setMethod(request.getMethod());
//        webLog.setParameter(getParameter(method, joinPoint.getArgs()));
//        webLog.setResult(result);
//        webLog.setSpendTime((int) (endTime - startTime));
//        webLog.setStartTime(startTime);
//        webLog.setUri(request.getRequestURI());
//        webLog.setUrl(request.getRequestURL().toString());
////        LOGGER.info("{}", JSONUtil.parse(webLog));
//        Map<String, Object> logMap = new HashMap<>();
//        logMap.put("url", webLog.getUrl());
//        logMap.put("method", webLog.getMethod());
//        logMap.put("parameter", webLog.getParameter());
//        logMap.put("spendTime", webLog.getSpendTime());
//        logMap.put("description", webLog.getDescription());
//        LOGGER.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());
//        return result;

//        return null;
//
//    }


    @Pointcut("execution(public * com.zhaofang.yushu.controller.*.*(..))")
    public void webLog() {


    }





    /**
     * 根据方法和传入的参数获取请求参数
     * @param method
     * @param args
     * @return
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }




}