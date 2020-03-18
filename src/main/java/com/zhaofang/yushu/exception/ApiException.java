package com.zhaofang.yushu.exception;

import com.zhaofang.yushu.entity.IErrorCode;

/**
 * 自定义API异常
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode iErrorCode){
        super(iErrorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Integer responseCode_illegal_argument) {
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

}
