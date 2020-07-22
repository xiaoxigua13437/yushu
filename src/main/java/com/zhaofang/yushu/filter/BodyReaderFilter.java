package com.zhaofang.yushu.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;


/**
 * 拦截请求，添加自定义请求逻辑(获取请求参数)
 * 步骤：将取出来的字符串，再次转换成流，然后把它放入到新request 对象中
 * @author yushu
 * @create 2020-06-18 14:00
 */
//@WebFilter(filterName = "bodyReaderFilter",urlPatterns = "/*",initParams = {
//        @WebInitParam(name = "exclusions"),
//})
public class BodyReaderFilter implements Filter{

    private static ThreadLocal<Map<String,Object>> myThreadLocal = new NamedThreadLocal<>("My ThreadLocal");

    private final static Integer MAX_SHOW_LENGTH = 1000;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String contentType = request.getContentType();
        String method = ((HttpServletRequest) request).getMethod();
        //设置请求时间
        BodyReaderFilter.addValueToMyThreadLocal("requestBeginTime",System.currentTimeMillis());
        //如果post请求使用application/json,application/x-www-form-urlencoded,text/xml请求方式，则用自定义请求拦截来获取请求参数
        if (contentType != null && "post".equalsIgnoreCase(method) &&
                (contentType.toLowerCase().startsWith("application/json") ||
                 contentType.toLowerCase().startsWith("text/xml"))){

            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(request,response);

        }else {
            String paramters = formateParameter(request);
            //获取请求参数
            BodyReaderFilter.addValueToMyThreadLocal("requestParamters", paramters);
            chain.doFilter(request, response);
        }


    }



    /**
     * 添加值到线程变量
     * @param key
     * @param value
     */
    public static void addValueToMyThreadLocal(String key,Object value) {
        Map<String,Object> map = myThreadLocal.get();
        if (map == null){
            map = new HashMap<>();
        }

        map.put(key,value);
        myThreadLocal.set(map);

    }

    /**
     * 获取线程变量的值
     * @param key
     * @return
     */
    public static Object getValueMyThreadLocal(String key){
        return myThreadLocal.get().get(key);
    }


    /**
     * 销毁线程变量
     */
    @Override
    public void destroy() {
        myThreadLocal.remove();
    }

    /**
     * 格式化参数
     */
    private String formateParameter(ServletRequest request) {
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
