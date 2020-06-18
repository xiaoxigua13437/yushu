package com.zhaofang.yushu.dto;

public class ResponseInfo {

    private long code;

    private String msg;

    private Object data;


    public ResponseInfo(){
        super();
    }

    public ResponseInfo(long code,String msg){
        super();
        this.code = code;
        this.msg = msg;
    }


    public ResponseInfo(long code,String msg,Object data){
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }



    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
