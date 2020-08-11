package com.zhaofang.yushu.interceptor;

import com.zhaofang.yushu.common.LoggerUtils;
import com.zhaofang.yushu.common.ParamUtil;
import com.zhaofang.yushu.common.threadpool.ThreadUtil;
import com.zhaofang.yushu.component.WebLogAspect;
import com.zhaofang.yushu.dto.ApiAccessLog;
import com.zhaofang.yushu.filter.BodyReaderFilter;
import com.zhaofang.yushu.service.ApiAccessLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * @author yushu
 * @create 2020/07/21 15:05
 */
public class WlanScopeLogInterceptor extends LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WlanScopeLogInterceptor.class);

    @Autowired
    private ApiAccessLogService apiAccessLogService;

    /**
     * 设置日志信息
     */
    public static final ThreadLocal<ApiAccessLog> logInfoThreadLocal = new NamedThreadLocal(
            "ThreadLocal  LogInfo");

    @Override
    public void saveLog(HttpServletRequest request, HttpServletResponse response) {
        //获取请求,返回参数
        String clientIp = LoggerUtils.getCliectIp(request);
        String requestUserAgent = LoggerUtils.getClientUserAgent(request);
        String url = request.getRequestURI();
        String requestType = LoggerUtils.getRequestType(request);
        String method = request.getMethod();
        String requestParamters = ParamUtil.StringParam(BodyReaderFilter.getValueToMyThreadLocal("requestParamters"));
        Long requestTime = ParamUtil.LongParam(BodyReaderFilter.getValueToMyThreadLocal("requestBeginTime"));
        String responseCode = ParamUtil.StringParam(BodyReaderFilter.getValueToMyThreadLocal("responseCode"));
        String responseParamters = ParamUtil.StringParam(BodyReaderFilter.getValueToMyThreadLocal("responseParamters"));

        //将数据保存到线程变量
        ApiAccessLog apiAccessLog = new ApiAccessLog();
        apiAccessLog.setReqClientIp(clientIp);
        apiAccessLog.setReqUserAgent(requestUserAgent);
        apiAccessLog.setReqUri(url);
        apiAccessLog.setReqType(requestType);
        apiAccessLog.setReqMethod(method);
        apiAccessLog.setReqData(requestParamters);
        apiAccessLog.setReqTime(requestTime);
        apiAccessLog.setRepTime(System.currentTimeMillis());
        apiAccessLog.setRepData(responseParamters);
        apiAccessLog.setRqpHttpStatusCode(responseCode);
        if (requestTime != null) {
            apiAccessLog.setCostTime((int) (System.currentTimeMillis() - requestTime));
        }

        //异步保存日志
        ThreadUtil.excAsync(new SaveLogThread(apiAccessLog),true);

        //销毁线程变量
        logInfoThreadLocal.remove();


    }



    private class SaveLogThread implements Runnable {
        private ApiAccessLog log;

        public SaveLogThread(ApiAccessLog log) {
            this.log = log;
        }

        @Override
        public void run() {
            apiAccessLogService.save(log);
        }
    }
}
