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


    /**
     * 正则查找是否含有某字符串
     *
     * @param validateString 需要验证的字符串
     * @param regEx 匹配规则
     * @return true-有  false-没有
     */
    public static Boolean regularFind(String validateString,String regEx){

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(validateString);
        if (matcher.find()){
            return true;
        }else {
            return false;
        }

    }


    /**
     * 查找匹配的字符
     *
     * @param validateString 需要验证的字符
     * @param regEx 匹配规则
     * @return
     */
    public static String regularFindWrite(String validateString,String regEx){

        StringBuilder builder = new StringBuilder();
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(validateString);
        if (matcher.find()){
            builder.append(matcher.group());
        }

        return builder.toString();
    }


    /**
     * ######################################################
     * #=========================匹配规则====================#
     * ######################################################
     */


    /**
     * 普通字符匹配
     */
    public void normalStr(){
        String str1="ab";
        String regEx1="ab";
        System.out.println("normalChar1:"+regularMatching(str1,regEx1));

    }




























    /**
     * unicode编码转中文
     *
     * @param dataStr
     * @return
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        System.out.println(buffer.toString());
        return buffer.toString();
    }


    public static void main(String[] args){

        String str = "throw 'allowScriptTagRemoting is false.';//#DWR-INSERT//#DWR-REPLYvar s0=[];var s1=[];s0[0]=null;s0[1]=null;s0[2]=null;s0[3]=null;s0[4]=null;s0[5]=null;s0[6]=null;s0[7]=null;s0[8]=null;s0[9]=null;s0[10]=null;s0[11]=null;s0[12]=null;s0[13]=null;s0[14]=null;s0[15]=null;s0[16]=null;s0[17]=null;s0[18]=null;s0[19]=null;s0[20]=null;s0[21]=null;s0[22]=null;s0[23]=null;s0[24]=null;s0[25]=null;s0[26]=null;s0[27]=null;s0[28]=null;s0[29]=null;s0[30]=null;s0[31]=null;s0[32]=null;s0[33]=null;s0[34]=null;s0[35]=null;dwr.engine._remoteHandleException('1','0',{cause:null,localizedMessage:\"Session\\u8FC7\\u671F\\uFF0C\\u8BF7\\u91CD\\u65B0\\u767B\\u5165\\uFF01\",message:\"Session\\u8FC7\\u671F\\uFF0C\\u8BF7\\u91CD\\u65B0\\u767B\\u5165\\uFF01\",stackTrace:s0,suppressed:s1});";
        //正则表达式取出{}中的内容
        String reg = "(\\{([^\\}]+)\\})";
        String res = PatternUtil.regularFindWrite(str,reg);

        //正则去除{}
        String regular ="(\\{|})";
        System.out.println(res.replaceAll(regular,""));

        /*String test = "\\u8FC7\\u671F\\uFF0C\\u8BF7\\u91CD\\u65B0\\u767B\\u5165\\uFF01";
        System.out.println(PatternUtil.decodeUnicode(test));*/













    }









}
