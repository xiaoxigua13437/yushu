package com.zhaofang.yushu.interceptor;


import com.zhaofang.yushu.common.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;

public abstract class LogInterceptor {

    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private static final int MAX_SHOW_LENGTH = 30;
    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal("ThreadLocal StartTime");
    public static final ThreadLocal<Map<String, Object>> executeResultThreadLocal = new NamedThreadLocal("Execute Result");

    public LogInterceptor(){}

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(this.logger.isDebugEnabled()) {
            long beginTime = System.currentTimeMillis();
            startTimeThreadLocal.set(Long.valueOf(beginTime));
            this.logger.debug("开始计时:{}  URI: {} ", (new SimpleDateFormat("hh:mm:ss.SSS")).format(Long.valueOf(beginTime)), request.getRequestURI());
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.saveLog(request, response);
        if(this.logger.isDebugEnabled()) {
            long beginTime = ((Long)startTimeThreadLocal.get()).longValue();
            long endTime = System.currentTimeMillis();
//            this.logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m", new Object[]{(new SimpleDateFormat("hh:mm:ss.SSS")).format(Long.valueOf(endTime)), DateUtil.formatDateTime(endTime - beginTime), request.getRequestURI(), Long.valueOf(Runtime.getRuntime().maxMemory() / 1024L / 1024L), Long.valueOf(Runtime.getRuntime().totalMemory() / 1024L / 1024L), Long.valueOf(Runtime.getRuntime().freeMemory() / 1024L / 1024L)});
        }

    }

    public abstract void saveLog(HttpServletRequest var1, HttpServletResponse var2);


}
