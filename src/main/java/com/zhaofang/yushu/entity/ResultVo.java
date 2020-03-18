package com.zhaofang.yushu.entity;

public class ResultVo<T> {

    private Integer code;
    private String message;

    private T data;

    public ResultVo(){}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static  ResultVo getFailedResult(int errorCode, String message) {

        ResultVo vo = new ResultVo();
        vo.setCode(errorCode);
        vo.setMessage(message);
        return  vo;
    }
}
