package com.zhaofang.yushu.base;

import com.zhaofang.yushu.entity.ResponseResult;
import com.zhaofang.yushu.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Iterator;
import java.util.List;

public class BaseController{


    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
    }

    protected void doValidateHandler(BindingResult bindingResult) {
        List objectErrorList = bindingResult.getGlobalErrors();
        StringBuffer sb = new StringBuffer();
        sb.append("请求参数错误:");
        Iterator fieldErrorList = objectErrorList.iterator();

        while(fieldErrorList.hasNext()) {
            ObjectError objectError = (ObjectError)fieldErrorList.next();
            sb.append(objectError.getDefaultMessage()).append(";");
        }

        List fieldErrorList1 = bindingResult.getFieldErrors();
        Iterator objectError1 = fieldErrorList1.iterator();

        while(objectError1.hasNext()) {
            FieldError fieldError = (FieldError)objectError1.next();
            sb.append(fieldError.getDefaultMessage()).append(";");
        }

        throw new ServiceException(BaseErrMsg.ERR_2, sb.toString());
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseResult bindException(Exception ex) {
        ResponseResult responseEntity = null;
        if(ex instanceof ServiceException) {
            ServiceException serviceException = (ServiceException)ex;
            if(serviceException.getErrCode() != null) {
                responseEntity = new ResponseResult(serviceException.getErrCode(), serviceException.getErrMsg(), (Object)null);
            } else {
                logger.error(ex.getMessage(), ex);
                responseEntity = new ResponseResult(BaseErrMsg.ERR_1);
            }
        } else {
            logger.error(ex.getMessage(), ex);
            responseEntity = new ResponseResult(BaseErrMsg.ERR_1);
        }

        return responseEntity;
    }


}
