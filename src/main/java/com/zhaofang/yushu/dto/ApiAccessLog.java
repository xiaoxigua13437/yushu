package com.zhaofang.yushu.dto;


import java.io.Serializable;


public class ApiAccessLog implements Serializable {

    /***/
    private Integer id;

    /**
     * 客户端请求IP地址
     */
    private String reqClientIp;

    /**
     * 客户端请求头信息地址
     */
    private String reqUserAgent;

    /**
     * 日志请求地址
     */
    private String reqUri;

    /**
     * 终端请求方式,普通请求,ajax请求
     */
    private String reqType;

    /**
     * 请求方式method,post,get等
     */
    private String reqMethod;

    /**
     * 请求参数内容,json
     */
    private String reqData;

    /**
     * 请求接口唯一session标识
     */
    private String reqSessionId;

    /**
     * 请求时间
     */
    private Long reqTime;

    /**
     * 接口返回时间
     */
    private Long repTime;

    /**
     * 接口返回数据json
     */
    private String repData;

    /**
     * 请求时httpStatusCode代码，如：200,400,404等
     */
    private String rqpHttpStatusCode;

    /**
     * 请求耗时（毫秒单位）
     */
    private int costTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReqClientIp() {
        return reqClientIp;
    }

    public void setReqClientIp(String reqClientIp) {
        this.reqClientIp = reqClientIp;
    }

    public String getReqUserAgent() {
        return reqUserAgent;
    }

    public void setReqUserAgent(String reqUserAgent) {
        this.reqUserAgent = reqUserAgent;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getReqSessionId() {
        return reqSessionId;
    }

    public void setReqSessionId(String reqSessionId) {
        this.reqSessionId = reqSessionId;
    }

    public Long getReqTime() {
        return reqTime;
    }

    public void setReqTime(Long reqTime) {
        this.reqTime = reqTime;
    }

    public Long getRepTime() {
        return repTime;
    }

    public void setRepTime(Long repTime) {
        this.repTime = repTime;
    }

    public String getRepData() {
        return repData;
    }

    public void setRepData(String repData) {
        this.repData = repData;
    }

    public String getRqpHttpStatusCode() {
        return rqpHttpStatusCode;
    }

    public void setRqpHttpStatusCode(String rqpHttpStatusCode) {
        this.rqpHttpStatusCode = rqpHttpStatusCode;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

}
