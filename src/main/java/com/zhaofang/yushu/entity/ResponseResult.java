package com.zhaofang.yushu.entity;

import com.zhaofang.yushu.base.BaseErrMsg;

import java.util.HashMap;

public class ResponseResult {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private Object result;

    public ResponseResult() {
        this.code = BaseErrMsg.SUCC_0;
        this.msg = this.getMsgByCode(this.code);
        this.result = new HashMap();
    }

    public ResponseResult(Integer errCode) {
        this.code = errCode;
        this.msg = this.getMsgByCode(this.code);
        this.result = new HashMap();
    }

    public ResponseResult(Integer errCode, String detailMsg) {
        this(errCode);
        this.msg = this.msg.concat(":" + detailMsg);
    }

    public ResponseResult(Integer errCode, Object result) {
        this.code = errCode;
        this.msg = this.getMsgByCode(this.code);
        if(result == null) {
            this.result = new HashMap();
        } else {
            this.result = result;
        }

    }





    public ResponseResult(Integer errCode, String errMsg, Object result) {
        this.code = errCode;
        if(errMsg != null) {
            this.msg = errMsg;
        } else {
            this.msg = this.getMsgByCode(errCode);
        }

        if(result == null) {
            this.result = new HashMap();
        } else {
            this.result = result;
        }

    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    private String getMsgByCode(Integer errCode) {
        return BaseErrMsg.getConfig(errCode);
    }

}
