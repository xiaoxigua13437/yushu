package com.zhaofang.yushu.exception;

import cn.hutool.core.util.StrUtil;
import com.zhaofang.yushu.entity.CommonResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }


    /**
     *
     * 全局参数异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResult handler(Exception exception) {

        StringBuilder errMsg = new StringBuilder();
        // 方法参数无效 异常
        if(exception instanceof MethodArgumentNotValidException) {
            BindingResult bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            List<FieldError> fieldErrorList = bindResult.getFieldErrors();
            fieldErrorList.forEach(fieldErrors -> {
                        FieldError fieldError = fieldErrors;
                        if (StrUtil.isNotBlank(errMsg.toString())) {
                            errMsg.append(",");
                        }
                        errMsg.append(fieldError.getDefaultMessage());
                    }
            );

        }else if (exception instanceof ConstraintViolationException) {
            // 约束冲突异常

        }
//        log.error("异常：",exception);
        return CommonResult.failed(errMsg.toString());
    }




}
