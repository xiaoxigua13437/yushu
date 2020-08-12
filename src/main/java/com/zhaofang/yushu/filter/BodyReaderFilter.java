package com.zhaofang.yushu.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * 拦截请求，添加自定义请求逻辑(获取请求参数)
 * 步骤：将取出来的字符串，再次转换成流，然后把它放入到新request 对象中
 *
 * @author yushu
 * @create 2020-08-11 11:10
 */
@WebFilter(filterName = "bodyReaderFilter",urlPatterns = "/*")
public class BodyReaderFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(BodyReaderFilter.class);

    private static ThreadLocal<Map<String, Object>> myThreadLocal = new NamedThreadLocal<>("My ThreadLocal");

    //最大请求报文数
    private final static Integer MAX_SHOW_LENGTH = 1024;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("初始化过滤器：{}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = null;
        String requestType = null;
        String method = null;

      /*会出现运行时错误，需要用instanceOf判断
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;*/
        //向下转型
        if (request instanceof HttpServletRequest) {
            httpServletRequest = (HttpServletRequest) request;
            requestType = request.getContentType();
            method = ((HttpServletRequest) request).getMethod();
        }
        //设置请求时间
        BodyReaderFilter.addValueToMyThreadLocal("requestBeginTime",System.currentTimeMillis());
        //如果post请求使用application/json,application/x-www-form-urlencoded,text/xml请求方式，则用自定义请求拦截来获取请求参数
        if (requestType != null && "post".equalsIgnoreCase(method) && (requestType.toLowerCase().startsWith("application/json"))){

            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper,response);

        }else {
            String parameters = FormatParameter(request);
            //将获取的请求参数写入到线程变量里
            BodyReaderFilter.addValueToMyThreadLocal("requestParameters",parameters);
            chain.doFilter(request, response);
        }



    }

    /**
     * 添加值到线程变量
     *
     * @param key 存储的key
     * @param value 对应的值
     */
    public static void addValueToMyThreadLocal(String key,Object value){
        Map<String,Object> map = myThreadLocal.get();
        if (map == null){
            map = new HashMap<>();
        }
        map.put(key, value);
        myThreadLocal.set(map);
    }

    /**
     * 获取线程变量的值
     *
     * @param key 存储的key
     * @return
     */
    public static Object getValueToMyThreadLocal(String key){
        return myThreadLocal.get().get(key);
    }

    /**
     * 销毁该线程变量
     */
    @Override
    public void destroy() {
        myThreadLocal.remove();
    }


    /**
     * 格式化参数
     *
     * @param request
     * @return
     */
    private String FormatParameter(ServletRequest request) {
        //日志参数格式化
        StringBuilder params = new StringBuilder("");
        Map<String, String[]> paramMap = request.getParameterMap();
        params.append("{");
        if (paramMap != null) {
            for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
                params.append(("".equals(params.toString()) ? "" : ",") + param.getKey() + "=");
                String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param
                        .getValue()[0] : "");
                String paramStr =
                        StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue;
                params.append(paramStr.length() <= MAX_SHOW_LENGTH ? paramStr
                        : (paramStr.substring(0, MAX_SHOW_LENGTH - 3) + "..."));
            }
        }
        params.append("}");
        return params.toString();
    }



}
