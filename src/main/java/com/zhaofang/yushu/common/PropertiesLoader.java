package com.zhaofang.yushu.common;


import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
//import org.apache.commons.io.IOUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


/**
 * 加载器
 *
 * @author yushu
 * @create 2020-04-11 10:18
 */
public class PropertiesLoader {

    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties;

    public PropertiesLoader(String... resourcesPaths) {
        this.properties = this.loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return this.properties;
    }

    private String getValue(String key) {
        return this.properties.containsKey(key)?this.properties.getProperty(key):"";
    }

    public String getString(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return value;
        }
    }

    public String getString(String key, String defaultValue) {
        String value = this.getValue(key);
        return value != null?value:defaultValue;
    }

    public Integer getInteger(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Integer.valueOf(value);
        }
    }

    public Integer getInteger(String key, Integer defaultValue) {
        String value = this.getValue(key);
        return value != null?Integer.valueOf(value):defaultValue;
    }

    public Double getDouble(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Double.valueOf(value);
        }
    }

    public Double getDouble(String key, Integer defaultValue) {
        String value = this.getValue(key);
        return Double.valueOf(value != null?Double.valueOf(value).doubleValue():(double)defaultValue.intValue());
    }

    public Boolean getBoolean(String key) {
        String value = this.getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Boolean.valueOf(value);
        }
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = this.getValue(key);
        return Boolean.valueOf(value != null?Boolean.valueOf(value).booleanValue():defaultValue);
    }

    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();
        String[] var3 = resourcesPaths;
        int var4 = resourcesPaths.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String location = var3[var5];
            InputStream is = null;

            try {
                Resource ex = resourceLoader.getResource(location);
                is = ex.getInputStream();
                props.load(is);
            } catch (IOException var12) {
                logger.info("Could not load properties from path:" + location + ", " + var12.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
            }
        }

        return props;
    }

}
