package com.zhaofang.yushu.common.regular;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式匹配规则
 *
 * @author yushu
 * @create 2020-06-21 09:56
 */
public class PatternUtil {


    /**
     * 正则匹配
     *
     * @param validateString 需要验证的字符
     * @param regEx 匹配规则
     * @return true-能匹配 false-不能匹配
     */
    public static Boolean regularMatching(String validateString,String regEx){

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(validateString);
        return matcher.matches();

    }









}
