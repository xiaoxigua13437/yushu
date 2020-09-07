package com.zhaofang.yushu.exception;

import cn.hutool.core.util.StrUtil;
import com.zhaofang.yushu.entity.CommonResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yushu
 * @create 2020-08-16
 *
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义API异常处理(凡是抛出ApiException 都会被捕捉到)
     *
     * @param e
     * @return
     */
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
     * 当使用了 @Validated + @RequestBody 注解但是没有在绑定的数据对象后面跟上 Errors 类型的参数声明的话，
     * Spring MVC 框架会抛出 MethodArgumentNotValidException 异常。
     * 当抛出 MethodArgumentNotValidException 异常,就会被相应的异常处理捕捉到理(有点类似于aop的意思)
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
