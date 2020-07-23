package com.zhaofang.yushu.config;

import com.zhaofang.yushu.common.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态读取配置文件工具类
 *
 * @author yushu
 * @create 2020-04-24 14:13
 */
public class ConfigPropertiesConfig {

    /**
     * 当前对象实例
     */
    private static ConfigPropertiesConfig configPropertiesConfig = new ConfigPropertiesConfig();

    /**
     * 保存全局属性值
     */
    private static Map<String,String> map = new HashMap<>();


    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader propertiesLoader = new PropertiesLoader(
            "config-wlanscope.properties");


    /**
     * 获取当前对象实例
     *
     * @return configProperties
     */
    private static ConfigPropertiesConfig getInstance(){
        return configPropertiesConfig;
    }


    /**
     * 获取配置文件中的参数
     *
     * @param key
     * @return value
     */
    public static String getConfig(String key){
        String value = map.get(key);
        if (value == null){
            value = propertiesLoader.getString(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;

    }




}
