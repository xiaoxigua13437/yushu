package com.zhaofang.yushu.test;



import com.alibaba.fastjson.JSONObject;
import com.zhaofang.yushu.common.regular.PatternUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegularTest {

    public static void main(String[] args) {
        String res_single = "";


        String res_list = "throw 'allowScriptTagRemoting is false.';\n" +
                "//#DWR-INSERT\n" +
                "//#DWR-REPLY\n" +
                "var s0={};var s1={};var s2={};var s3={};var s4={};var s5={};var s6={};var s7={};var s8={};var s9={};var s10={};var s11={};var s12={};var s13={};var s14={};var s15={};var s16={};var s17={};var s18={};var s19={};var s20={};var s21={};var s22={};var s23={};var s24={};var s25={};var s26={};var s27={};var s28={};var s29={};var s30={};s0.name=\"\u5B89\u5FBD\";s0.id=\"1\";\n" +
                "s1.name=\"\u5317\u4EAC\";s1.id=\"2\";\n" +
                "s2.name=\"\u798F\u5EFA\";s2.id=\"3\";\n" +
                "s3.name=\"\u7518\u8083\";s3.id=\"4\";\n" +
                "s4.name=\"\u5E7F\u4E1C\";s4.id=\"5\";\n" +
                "s5.name=\"\u5E7F\u897F\";s5.id=\"6\";\n" +
                "s6.name=\"\u8D35\u5DDE\";s6.id=\"7\";\n" +
                "s7.name=\"\u6D77\u5357\";s7.id=\"8\";\n" +
                "s8.name=\"\u6CB3\u5317\";s8.id=\"9\";\n" +
                "s9.name=\"\u6CB3\u5357\";s9.id=\"10\";\n" +
                "s10.name=\"\u9ED1\u9F99\u6C5F\";s10.id=\"11\";\n" +
                "s11.name=\"\u6E56\u5317\";s11.id=\"12\";\n" +
                "s12.name=\"\u6E56\u5357\";s12.id=\"13\";\n" +
                "s13.name=\"\u5409\u6797\";s13.id=\"14\";\n" +
                "s14.name=\"\u6C5F\u82CF\";s14.id=\"15\";\n" +
                "s15.name=\"\u6C5F\u897F\";s15.id=\"16\";\n" +
                "s16.name=\"\u8FBD\u5B81\";s16.id=\"17\";\n" +
                "s17.name=\"\u5185\u8499\u53E4\";s17.id=\"18\";\n" +
                "s18.name=\"\u5B81\u590F\";s18.id=\"19\";\n" +
                "s19.name=\"\u9752\u6D77\";s19.id=\"20\";\n" +
                "s20.name=\"\u5C71\u4E1C\";s20.id=\"21\";\n" +
                "s21.name=\"\u5C71\u897F\";s21.id=\"22\";\n" +
                "s22.name=\"\u9655\u897F\";s22.id=\"23\";\n" +
                "s23.name=\"\u4E0A\u6D77\";s23.id=\"24\";\n" +
                "s24.name=\"\u56DB\u5DDD\";s24.id=\"25\";\n" +
                "s25.name=\"\u5929\u6D25\";s25.id=\"26\";\n" +
                "s26.name=\"\u897F\u85CF\";s26.id=\"27\";\n" +
                "s27.name=\"\u65B0\u7586\";s27.id=\"28\";\n" +
                "s28.name=\"\u4E91\u5357\";s28.id=\"29\";\n" +
                "s29.name=\"\u6D59\u6C5F\";s29.id=\"30\";\n" +
                "s30.name=\"\u91CD\u5E86\";s30.id=\"31\";\n" +
                "dwr.engine._remoteHandleCallback('1','0',[s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30]);";

        String res_list_page = "throw 'allowScriptTagRemoting is false.';\n" +
                "//#DWR-INSERT\n" +
                "//#DWR-REPLY\n" +
                "var s01=[];var s1={};var s2={};var s3={};var s4={};var s5={};var s6={};var s7={};var s8={};var s9={};var s10={};var s11={};var s12={};var s13={};var s14={};var s15={};s0[0]=s1;s0[1]=s2;s0[2]=s3;s0[3]=s4;s0[4]=s5;s0[5]=s6;s0[6]=s7;s0[7]=s8;s0[8]=s9;s0[9]=s10;s0[10]=s11;s0[11]=s12;s0[12]=s13;s0[13]=s14;s0[14]=s15;\n" +
                "s1.timer=\"\u6574\u5929\";s1.st=\"1\";s1.rtime=\"2020-06-29\";s1.meet=\"21\u697C\uFF08\u5C0F\uFF09\";s1.usna=\"\u65B9\u6E49\";s1.ctime=\"2020-06-12 14:38:13\";s1.id=\"0000000071b052380172a7198eba0340\";s1.cont=\"HW\u9700\u8981\";s1.isty=\"\u9700\u8981\";s1.usid=\"808080805d1c8690015d34afbbfe0020\";s1.ogna=\"\u7F51\u7EDC\u548C\u4FE1\u606F\u5B89\u5168\u7BA1\u7406\u90E8\";\n" +
                "s2.timer=\"\u4E0A\u5348\";s2.st=\"1\";s2.rtime=\"2020-06-28\";s2.meet=\"19\u697C\uFF08\u6307\u6325\u8C03\u5EA6\u5BA4\uFF09\";s2.usna=\"\u5F20\u5EFA\u6210\";s2.ctime=\"2020-06-22 09:13:18\";s2.id=\"0000000071b052380172d96f5718036a\";s2.cont=\"\u98CE\u9669\u64CD\u4F5C\u4F8B\u4F1A\";s2.isty=\"\u9700\u8981\";s2.usid=\"7\";s2.ogna=\"\u7EFC\u5408\u5206\u6790\u5BA4\";\n" +
                "s3.timer=\"\u6574\u5929\";s3.st=\"1\";s3.rtime=\"2020-06-26\";s3.meet=\"21\u697C\uFF08\u5C0F\uFF09\";s3.usna=\"\u65B9\u6E49\";s3.ctime=\"2020-06-12 14:38:02\";s3.id=\"0000000071b052380172a7196354033f\";s3.cont=\"HW\u9700\u8981\";s3.isty=\"\u9700\u8981\";s3.usid=\"808080805d1c8690015d34afbbfe0020\";s3.ogna=\"\u7F51\u7EDC\u548C\u4FE1\u606F\u5B89\u5168\u7BA1\u7406\u90E8\";\n" +
                "s4.timer=\"\u6574\u5929\";s4.st=\"1\";s4.rtime=\"2020-06-25\";s4.meet=\"21\u697C\uFF08\u5C0F\uFF09\";s4.usna=\"\u65B9\u6E49\";s4.ctime=\"2020-06-12 14:37:45\";s4.id=\"0000000071b052380172a7192036033e\";s4.cont=\"HW\u9700\u8981\";s4.isty=\"\u9700\u8981\";s4.usid=\"808080805d1c8690015d34afbbfe0020\";s4.ogna=\"\u7F51\u7EDC\u548C\u4FE1\u606F\u5B89\u5168\u7BA1\u7406\u90E8\";\n" +
                "s5.timer=\"\u4E0A\u5348\";s5.st=\"1\";s5.rtime=\"2020-06-24\";s5.meet=\"20\u697C\";s5.usna=\"\u6731\u9F50\u9A71\";s5.ctime=\"2020-06-18 18:09:47\";s5.id=\"0000000071b052380172c6c0ebcf0362\";s5.cont=\"\u7F51\u4F18\u5E73\u53F0\u4F1A\u8BAE\";s5.isty=\"\u9700\u8981\";s5.usid=\"0000000071534e5b017185f45561008d\";s5.ogna=\"\u65E0\u7EBF\u7F51\u4F18\u4E2D\u5FC3\";\n" +
                "s6.timer=\"\u6574\u5929\";s6.st=\"1\";s6.rtime=\"2020-06-24\";s6.meet=\"21\u697C\uFF08\u5C0F\uFF09\";s6.usna=\"\u65B9\u6E49\";s6.ctime=\"2020-06-12 14:37:36\";s6.id=\"0000000071b052380172a718fe31033d\";s6.cont=\"HW\u9700\u8981\";s6.isty=\"\u9700\u8981\";s6.usid=\"808080805d1c8690015d34afbbfe0020\";s6.ogna=\"\u7F51\u7EDC\u548C\u4FE1\u606F\u5B89\u5168\u7BA1\u7406\u90E8\";\n" +
                "s7.timer=\"\u4E0A\u5348\";s7.st=\"1\";s7.rtime=\"2020-06-23\";s7.meet=\"20\u697C\";s7.usna=\"\u697C\u5982\u6D0B\";s7.ctime=\"2020-06-22 10:54:51\";s7.id=\"0000000071b052380172d9cc4d65036c\";s7.cont=\"\u4F20\u8F93\u4E1A\u52A1\u5F00\u901A\u8BA8\u8BBA\";s7.isty=\"\u9700\u8981\";s7.usid=\"06e0f7c64bc835e0014e0545f7ca0590\";s7.ogna=\"\u4F20\u8F93\u7F51\u7EF4\u62A4\u5BA4\";\n" +
                "s8.timer=\"\u4E0A\u5348\";s8.st=\"1\";s8.rtime=\"2020-06-23\";s8.meet=\"26\u697C\";s8.usna=\"\u9C81\u6587\u97EC\";s8.ctime=\"2020-06-22 11:13:23\";s8.id=\"0000000071b052380172d9dd439c036d\";s8.cont=\"\u5185\u90E8\u8BA8\u8BBA\";s8.isty=\"\u9700\u8981\";s8.usid=\"141\";s8.ogna=\"\u653F\u4F01\u5BA2\u6237\u652F\u6491\u4E2D\u5FC3\";\n" +
                "s9.timer=\"\u6574\u5929\";s9.st=\"1\";s9.rtime=\"2020-06-23\";s9.meet=\"21\u697C\uFF08\u5C0F\uFF09\";s9.usna=\"\u65B9\u6E49\";s9.ctime=\"2020-06-12 14:37:21\";s9.id=\"0000000071b052380172a718c359033c\";s9.cont=\"HW\u9700\u8981\";s9.isty=\"\u9700\u8981\";s9.usid=\"808080805d1c8690015d34afbbfe0020\";s9.ogna=\"\u7F51\u7EDC\u548C\u4FE1\u606F\u5B89\u5168\u7BA1\u7406\u90E8\";\n" +
                "s10.timer=\"\u4E0B\u5348\";s10.st=\"1\";s10.rtime=\"2020-06-23\";s10.meet=\"19\u697C\uFF08\u6307\u6325\u8C03\u5EA6\u5BA4\uFF09\";s10.usna=\"\u97E9\u71D5\u8389\";s10.ctime=\"2020-06-22 10:41:36\";s10.id=\"0000000071b052380172d9c02a98036b\";s10.cont=\"\u515A\u5C0F\u7EC4\u4F1A\";s10.isty=\"\u9700\u8981\";s10.usid=\"33\";s10.ogna=\"\u7F51\u7EDC\u76D1\u63A7\u5BA4\";\n" +
                "s11.timer=\"\u4E0A\u5348\";s11.st=\"1\";s11.rtime=\"2020-06-22\";s11.meet=\"20\u697C\";s11.usna=\"\u5F20\u52C7\";s11.ctime=\"2020-06-16 14:50:33\";s11.id=\"0000000071b052380172bbbe192e0356\";s11.cont=\"800M\u6269\u9891\u91CD\u8015\u4F1A\u8BAE\";s11.isty=\"\u9700\u8981\";s11.usid=\"40287281715e2d4801715e426160000b\";s11.ogna=\"\u65E0\u7EBF\u7F51\u4F18\u4E2D\u5FC3\";\n" +
                "s12.timer=\"\u4E0A\u5348\";s12.st=\"1\";s12.rtime=\"2020-06-22\";s12.meet=\"26\u697C\";s12.usna=\"\u5415\u9526\u5F6C\";s12.ctime=\"2020-06-22 09:01:27\";s12.id=\"0000000071b052380172d9647a330369\";s12.cont=\"\u4F1A\u8BAE\";s12.isty=\"\u9700\u8981\";s12.usid=\"06e0f7c648638f97014adc97d13d0595\";s12.ogna=\"\u7CFB\u7EDF\u652F\u6491\u4FDD\u969C\u5BA4\";\n" +
                "s13.timer=\"\u4E0A\u5348\";s13.st=\"1\";s13.rtime=\"2020-06-22\";s13.meet=\"19\u697C\uFF08\u6307\u6325\u8C03\u5EA6\u5BA4\uFF09\";s13.usna=\"\u5F20\u5EFA\u6210\";s13.ctime=\"2020-06-15 09:14:36\";s13.id=\"0000000071b052380172b564459d0343\";s13.cont=\"\u98CE\u9669\u64CD\u4F5C\u4F8B\u4F1A\";s13.isty=\"\u9700\u8981\";s13.usid=\"7\";s13.ogna=\"\u7EFC\u5408\u5206\u6790\u5BA4\";\n" +
                "s14.timer=\"\u6574\u5929\";s14.st=\"1\";s14.rtime=\"2020-06-22\";s14.meet=\"21\u697C\uFF08\u5C0F\uFF09\";s14.usna=\"\u65B9\u6E49\";s14.ctime=\"2020-06-12 14:37:11\";s14.id=\"0000000071b052380172a7189b61033b\";s14.cont=\"HW\u9700\u8981\";s14.isty=\"\u9700\u8981\";s14.usid=\"808080805d1c8690015d34afbbfe0020\";s14.ogna=\"\u7F51\u7EDC\u548C\u4FE1\u606F\u5B89\u5168\u7BA1\u7406\u90E8\";\n" +
                "s15.timer=\"\u4E0B\u5348\";s15.st=\"1\";s15.rtime=\"2020-06-22\";s15.meet=\"19\u697C\uFF08\u6307\u6325\u8C03\u5EA6\u5BA4\uFF09\";s15.usna=\"\u674E\u4FCA\";s15.ctime=\"2020-06-16 16:03:19\";s15.id=\"0000000071b052380172bc00c8650357\";s15.cont=\"\u963F\u67177750\u4EA7\u54C1\u57F9\u8BAD\";s15.isty=\"\u9700\u8981\";s15.usid=\"06e0f7c653547e80015497615d010286\";s15.ogna=\"\u6570\u636E\u7F51\u7EF4\u62A4\u5BA4\";\n" +
                "dwr.engine._remoteHandleCallback('2','0',{pS:15,tP:448,bN:1,list:s0,cP:1,aN:6706});";
//        analysisPageList(res_list_page);

        String str = "{pS:15,tP:448,bN:1,list:s0,cP:1,aN:6706}";
        str = str.replaceAll("list.+?,", "");
        System.out.println(str);
        JSONObject jsonObject = JSONObject.parseObject(str);
//        Bean bean = JSONObject.parseObject(str, Bean.class);
        System.out.println(jsonObject);
    }

    public static JSONObject analysisSingle(String str) {
        str = str.replaceAll("\"", "");
        String regex = "(?<=(dwr.engine._remoteHandleCallback\\())(.+?)(?=\\))";
        String res = PatternUtil.regularFindWrite(str, regex);
        regex = "(?=\\{)(.*?)(?<=})";
        res = PatternUtil.regularFindWrite(res, regex);
        JSONObject jsonObject = JSONObject.parseObject(res);
        System.out.println(jsonObject);
        return jsonObject;

    }

}
/*public static void analysisList(String str) {
        str = str.replaceAll("\"", "");
        String regex = "(?<=(dwr.engine._remoteHandleCallback\\())(.+?)(?=\\))";
        String res = RegexUtil.getMatcherStr(str, regex);
        regex = "(?<=\\[)(.*?)(?=])";
        res = RegexUtil.getMatcherStr(res, regex);
        String[] split = res.split(",");
        for (int i=0; i<split.length; i++) {
            Map<Object, Object> objectObjectMap = matcherMap(str, split[i]);
            String s = JSONObject.toJSONString(objectObjectMap);
            System.out.println(s);
        }
    }*//*


    public static void analysisList(String str) {
        str = str.replaceAll("\"", "");
        String regex = "(?<=(var ))(s(?!0).*?)(?=(\\=))";
        List<String> matcherList = RegexUtil.getMatcherList(str, regex);
        for (String key : matcherList) {
            Map<Object, Object> objectObjectMap = matcherMap(str, key);
            String s = JSONObject.toJSONString(objectObjectMap);
            System.out.println(s);
        }
    }

    public static void analysisPageList(String str) {
        analysisList(str);
        JSONObject jsonObject = analysisSingle(str);
        System.out.println(jsonObject);

    }

    public static Map<Object, Object> matcherMap(String matchStr, String key) {
        Map<Object, Object> resMap = new HashMap<>();
//        matchStr = matchStr.replaceAll("\"", "");
        String regStr = key + "\\.(.+?)=(.+?);";
        if (StringUtils.isNotEmpty(matchStr)) {
            Pattern pattern = Pattern.compile(regStr);
            Matcher matcher = pattern.matcher(matchStr);
            while (matcher.find()) {
                resMap.put(matcher.group(1), matcher.group(2));
            }
        }
        return resMap;
    }

}
*/
