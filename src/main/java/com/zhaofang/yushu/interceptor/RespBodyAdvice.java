package com.zhaofang.yushu.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhaofang.yushu.entity.ResponseResult;
import com.zhaofang.yushu.filter.BodyReaderFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 拦截controller方法默认返回参数,统一处理返回值/响应体
 *
 * @author yushu
 * @create 2020-08-12 14:34
 */
@ControllerAdvice
public class RespBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger logger = LoggerFactory.getLogger(RespBodyAdvice.class);


    /**
     *
     * 返回结果集操作的body
     *
     * @param object
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (object instanceof ResponseResult && object != null){

            ResponseResult responseResult = (ResponseResult) object;
            //打印日志
            if (logger.isDebugEnabled()){
                logger.debug("返回参数:" + JSON.toJSONString(object));
            }
            if (responseResult.getCode() !=null){
                //状态响应码写入到线程变量
                BodyReaderFilter.addValueToMyThreadLocal("responseCode",responseResult.getCode());
            }

            if (responseResult.getResult() !=null){
                String result = JSON.toJSONString(responseResult.getResult());
                //返回result写入到线程变量
                BodyReaderFilter.addValueToMyThreadLocal("responseParameters",result);
            }
        }

        return object;
    }


    /**
     * 支持给定的控制器方法返回值类型
     *
     * @param methodParameter the return type
     * @param aClass the selected converter type
     * @return true-返回调用处理方法
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

}
