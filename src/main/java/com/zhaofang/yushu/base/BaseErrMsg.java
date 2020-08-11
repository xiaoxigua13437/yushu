package com.zhaofang.yushu.base;

import com.zhaofang.yushu.common.PropertiesLoader;
import java.util.HashMap;
import java.util.Map;

public class BaseErrMsg {

    private final String errFileName = "errors.properties";
    protected static PropertiesLoader propertiesLoader;
    protected static Map<Integer, String> errorsMap = new HashMap();
    public static final Integer SUCC_0 = Integer.valueOf(0);
    public static final Integer ERR_1 = Integer.valueOf(1);
    public static final Integer ERR_2 = Integer.valueOf(2);
    public static final Integer ERR_3 = Integer.valueOf(3);
    public static final Integer ERR_4 = Integer.valueOf(4);

    protected BaseErrMsg(String... childErrFiles) {
        String[] allFiles = new String[childErrFiles.length + 1];
        allFiles[0] = "errors.properties";
        System.arraycopy(childErrFiles, 0, allFiles, 1, childErrFiles.length);
        propertiesLoader = new PropertiesLoader(allFiles);
    }

    public static String getConfig(Integer erroCode) {
        String value = (String)errorsMap.get(erroCode);
        if(value == null) {
            try {
                value = propertiesLoader.getString(erroCode.toString());
            } catch (Exception var3) {
                return "";
            }

            errorsMap.put(erroCode, value != null?value:"");
        }

        return value;
    }
}
