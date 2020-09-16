package com.zhaofang.yushu.common.authUtils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 浏览器判断通用工具类
 *
 * @author yushu
 * @create 2020-09-07 15:15
 */
public class UserAgentUtil {


    public static final String BROWSER_IE = "ie";
    public static final String BROWSER_OPERA = "opera";
    public static final String BROWSER_FIREFOX = "firefox";
    public static final String BROWSER_SAFARI = "safari";
    public static final String BROWSER_CHROME = "chrome";

    public static Map<String,String> mobileDevices = new HashMap<String,String>(){{
        put("iPhone", "iPhone");
        put("iPad", "iPad");
        put("Android", "Android");
        put("IEMobile", "IEMobile");
    }};

    public static String getMobileDeviceType(HttpServletRequest request){
        String mobileDeviceType = null;
        String ua = request.getHeader("user-agent");
        for (Map.Entry<String, String> entry : mobileDevices.entrySet()) {
            if(null != request && ua.indexOf(entry.getKey()) >=0){
                mobileDeviceType =  entry.getKey();
                break;
            }
        }
        return mobileDeviceType;
    }

    public static boolean isMobileDevice(HttpServletRequest request){
        if(null != getMobileDeviceType(request)){
            return true;
        }else{
            return false;
        }
    }

    public static String getMobileDeviceType(String userAgent){
        String mobileDeviceType = null;
        if(StringUtils.isNotEmpty(userAgent)){
            for (Map.Entry<String, String> entry : mobileDevices.entrySet()) {
                if(userAgent.indexOf(entry.getKey()) >=0){
                    mobileDeviceType =  entry.getKey();
                    break;
                }
            }
        }
        return mobileDeviceType;
    }

    public static boolean isMobileDevice(String userAgent){
        if(null != getMobileDeviceType(userAgent)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断浏览器
     *
     * @param request
     * @return
     */
    public static String getBrowserType(HttpServletRequest request) {
        String browserType = "";
        String ua = request.getHeader("USER-AGENT").toLowerCase();
        if (ua.indexOf("msie") > 0) {
            browserType = BROWSER_IE;
        }else if (ua.indexOf("opera") > 0) {
            browserType = BROWSER_OPERA;
        }else if (ua.indexOf("firefox") > 0) {
            browserType = BROWSER_FIREFOX;
        }else if (ua.indexOf("chrome") > 0) {
            browserType = BROWSER_CHROME;
        }else if (ua.indexOf("safari") > 0) {
            browserType = BROWSER_SAFARI;
        }else {
            browserType = BROWSER_IE;
        }
        return browserType;
    }
}
