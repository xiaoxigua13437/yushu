package com.zhaofang.yushu.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhaofang.yushu.dto.ResponseInfo;

/**
 * @author yushu
 * @create 2020-06-16 11:38
 * 服务器返回的json数据，使用fastjson
 */
public class JsonResponseUtil {

    //默认成功返回码
    private static final int DEFAULT_SUCCESS_STATUS = 100;
    //默认成功返回信息
    private static final String DEFAULT_SUCCESS_MSG = "成功";
    //默认失败返回码
    private static final int DEFAULT_FAIL_STATUS = 110;
    //默认失败返回信息
    private static final String DEFAULT_FAIL_MSG = "失败";

    /**
     * 操作成功并返回信息
     * @param data
     * @return
     */
    public static String getSuccessByData(Object data){
        ResponseInfo resultInfo = new ResponseInfo(DEFAULT_SUCCESS_STATUS,DEFAULT_SUCCESS_MSG);
        resultInfo.setData(data);
        return JSON.toJSONString(resultInfo);
    }

    public static String getSuccessByDataDis(Object data){
        ResponseInfo resultInfo = new ResponseInfo(DEFAULT_SUCCESS_STATUS,DEFAULT_SUCCESS_MSG);
        resultInfo.setData(data);
        return JSON.toJSONString(resultInfo, SerializerFeature.DisableCircularReferenceDetect);//消除对同一对象循环引用的问题，默认为false

    }


    /**
     * 操作成功并返回信息
     */
    public static String getSuccessByMsg(String msg) {
        ResponseInfo resultInfo = new ResponseInfo(DEFAULT_SUCCESS_STATUS, msg);
        return JSON.toJSONString(resultInfo);
    }

    /**
     * 操作成功并返回信息
     *
     * @return json
     */
    public static JSONObject getSuccessJsonByMsg(String msg) {
        ResponseInfo resultInfo = new ResponseInfo(DEFAULT_SUCCESS_STATUS, msg);
        return (JSONObject) JSON.toJSON(resultInfo);
    }

    /**
     * 操作成功并返回信息
     *
     * @return json
     */
    public static JSONObject getSuccessJsonByData(Object data) {
        ResponseInfo resultInfo = new ResponseInfo(DEFAULT_SUCCESS_STATUS, DEFAULT_SUCCESS_MSG);
        resultInfo.setData(data);
        return (JSONObject) JSON.toJSON(resultInfo);
    }

    /**
     * 操作成功
     */
    public static String getSuccess() {
        return getSuccessByData(null);
    }

    /**
     * 操作成功
     *
     * @return json
     */
    public static JSONObject getSuccessJson() {
        return getSuccessJsonByData(null);
    }

    /**
     * 操作失败
     */
    public static String getFail() {
        return getFailBySelf(DEFAULT_FAIL_STATUS, DEFAULT_FAIL_MSG);
    }

    /**
     * 操作失败，自定义失败提示
     *
     * @return json
     */
    public static JSONObject getFailJson() {
        return getFailJsonBySelf(DEFAULT_FAIL_STATUS, DEFAULT_FAIL_MSG);
    }

    /**
     * 操作失败，自定义失败提示
     */
    public static String getFailByMsg(String msg) {
        return getFailBySelf(DEFAULT_FAIL_STATUS, msg);
    }

    /**
     * 操作失败，自定义失败提示
     *
     * @return json
     */
    public static JSONObject getFailJsonByMsg(String msg) {
        return getFailJsonBySelf(DEFAULT_FAIL_STATUS, msg);
    }

    /**
     * 操作失败，自定义
     *
     * @return 字符串
     */
    public static String getFailBySelf(int code, String msg) {
        ResponseInfo resultInfo = new ResponseInfo(code, msg);
        return JSON.toJSONString(resultInfo);
    }

    /**
     * 操作失败，自定义
     *
     * @return json
     */
    public static JSONObject getFailJsonBySelf(int code, String msg) {
        ResponseInfo resultInfo = new ResponseInfo(code, msg);
        return (JSONObject) JSON.toJSON(resultInfo);
    }

    public static void main(String[] args) {
        System.out.println(JsonResponseUtil.getSuccessJsonByData("123"));
    }





}
