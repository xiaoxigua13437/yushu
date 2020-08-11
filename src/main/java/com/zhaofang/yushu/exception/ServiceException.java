package com.zhaofang.yushu.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer errCode;

    private String errMsg;

    public ServiceException(){}

    public ServiceException(Integer errCode) {
        this.errCode = errCode;
    }

    public ServiceException(Integer errCode, String message) {
        super(message);
        this.errCode = errCode;
        this.errMsg = message;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(Integer errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
        this.errMsg = message;
    }

    public ServiceException(Integer errCode, Throwable cause) {
        super("", cause);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return this.errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
