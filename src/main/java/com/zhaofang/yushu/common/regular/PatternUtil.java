package com.zhaofang.yushu.common.regular;


import java.util.ArrayList;
import java.util.List;
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
     * 正则表达式匹配两个指定字符串中间的内容
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static List<String> getSubUtil(String soap, String rgex){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
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
     * 转义字符匹配
     * 注： 回车:\r  换行：\n  制表符：\t  \本身：\\  ^本身：\^    $本身：\$   .本身：\.
     *
     *    括号本身：\( 和 \)   \[ 和 \]  \{ 和 \}     ?本身：\?     +本身：\+      *本身：\*    |本身： \|
     */
    public void transferChar(){
        String str1="anc$de";
        String regEx1="c\\$d";   //这边第一个\是转义字符串中的\ ， \\$ 即表示\$
        System.out.println("tranferChar1:"+regularFind(str1,regEx1)); //匹配"c$d"
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

        String str = "throw 'allowScriptTagRemoting is false.';\n" +
                "//#DWR-INSERT\n" +
                "//#DWR-REPLY\n" +
                "var s0=[];var s1={};var s2={};var s3={};var s4={};var s5={};var s6={};var s7={};var s8={};var s9={};var s10={};var s11={};var s12={};var s13={};var s14={};var s15={};s0[0]=s1;s0[1]=s2;s0[2]=s3;s0[3]=s4;s0[4]=s5;s0[5]=s6;s0[6]=s7;s0[7]=s8;s0[8]=s9;s0[9]=s10;s0[10]=s11;s0[11]=s12;s0[12]=s13;s0[13]=s14;s0[14]=s15;\n" +
                "s1.res=\"\\u67E5\\u8D26\\u548C\\u559D\\u9152\";s1.st=\"1\";s1.rtime=\"2020-07-30\";s1.ydnum=\"5\";s1.usname=\"\\u67F3\\u52A0\\u4F1F\";s1.spusid=\"4\";s1.stime=\"2020-07-30\";s1.dnum=\"1\";s1.usid=\"122\";s1.oname=\"\\u7CFB\\u7EDF\\u652F\\u6491\\u4FDD\\u969C\\u5BA4\";s1.spusname=\"\\u738B\\u6797\\u548C\";s1.etime=\"2020-07-31\";s1.sptime=null;s1.rk=null;s1.id=\"4028d3b7738ded0901739d92e6a40013\";s1.spst2=null;\n" +
                "s2.res=\"\\u548C\\u4F60\\u6709\\u5173\\u7CFB\\u4E48\";s2.st=\"1\";s2.rtime=\"2020-07-30\";s2.ydnum=\"5\";s2.usname=\"\\u67F3\\u52A0\\u4F1F\";s2.spusid=\"000000005b844c04015b88d4d04800f8\";s2.stime=\"2020-07-30\";s2.dnum=\"1\";s2.usid=\"122\";s2.oname=\"\\u7CFB\\u7EDF\\u652F\\u6491\\u4FDD\\u969C\\u5BA4\";s2.spusname=\"\\u5C01\\u5EFA\\u660E\";s2.etime=\"2020-08-31\";s2.sptime=null;s2.rk=null;s2.id=\"4028d3b7738ded0901739d9ef3610014\";s2.spst2=null;\n" +
                "s3.res=\"\\u56DE\\u5357\\u4EAC\";s3.st=\"1\";s3.rtime=\"2020-07-30\";s3.ydnum=\"5\";s3.usname=\"\\u5B89\\u96EA\\u6885\";s3.spusid=\"122\";s3.stime=\"2020-07-30\";s3.dnum=\"1\";s3.usid=\"0000000071534e5b0171aae1f6b200c0\";s3.oname=\"\\u7EFC\\u5408\\u5206\\u6790\\u5BA4\";s3.spusname=\"\\u67F3\\u52A0\\u4F1F\";s3.etime=\"2020-07-30\";s3.sptime=\"2020-07-30 10:24:44\";s3.rk=null;s3.id=\"4028d3b7738ded0901739d7adf9c0012\";s3.spst2=\"\\u540C\\u610F\";\n" +
                "s4.res=\"\\u8BF7\\u5E74\\u4F11\\u5047\\u4E00\\u5929\";s4.st=\"1\";s4.rtime=\"2020-06-17\";s4.ydnum=\"10\";s4.usname=\"\\u5F20\\u6653\\u71D5\";s4.spusid=\"100\";s4.stime=\"2020-06-18\";s4.dnum=\"1\";s4.usid=\"107\";s4.oname=\"\\u6570\\u636E\\u7F51\\u7EF4\\u62A4\\u5BA4\";s4.spusname=\"\\u5362\\u6D2A\\u4EAE\";s4.etime=\"2020-06-18\";s4.sptime=null;s4.rk=null;s4.id=\"0000000071b052380172c1475245035c\";s4.spst2=null;\n" +
                "s5.res=\"\\u5E74\\u4F110.5\\u5929\\uFF082020\\u5E74\\u4F11\\u5047\\uFF09\";s5.st=\"1\";s5.rtime=\"2020-06-16\";s5.ydnum=\"15\";s5.usname=\"\\u9F99\\u4E00\\u5DDD\";s5.spusid=\"06e0f7c6518f5ba8015232a45cba01fd\";s5.stime=\"2020-06-16\";s5.dnum=\"1\";s5.usid=\"88\";s5.oname=\"\\u4E91\\u7EF4\\u62A4\\u5BA4\";s5.spusname=\"\\u865E\\u5251\\u950B\";s5.etime=\"2020-06-17\";s5.sptime=\"2020-06-16 12:30:05\";s5.rk=null;s5.id=\"0000000071b052380172baff93ed0353\";s5.spst2=\"\\u540C\\u610F\";\n" +
                "s6.res=\"\\u8BF7\\u5E74\\u4F11\\u50471\\u5929\";s6.st=\"1\";s6.rtime=\"2020-06-08\";s6.ydnum=\"15\";s6.usname=\"\\u865E\\u5251\\u950B\";s6.spusid=\"6\";s6.stime=\"2020-06-09\";s6.dnum=\"1\";s6.usid=\"06e0f7c6518f5ba8015232a45cba01fd\";s6.oname=\"\\u4E91\\u7EF4\\u62A4\\u5BA4\";s6.spusname=\"\\u4F59\\u8363\";s6.etime=\"2020-06-09\";s6.sptime=\"2020-06-08 14:54:57\";s6.rk=null;s6.id=\"0000000071b052380172928c2b4a0313\";s6.spst2=\"\\u540C\\u610F\";\n" +
                "s7.res=\"\\u8865\\u767B6.3\\u65E5\\u8BF7\\u5E74\\u4F11\\u4E00\\u5929\";s7.st=\"1\";s7.rtime=\"2020-06-04\";s7.ydnum=\"5\";s7.usname=\"\\u90D1\\u9A81\\u80FD\";s7.spusid=\"57\";s7.stime=\"2020-06-04\";s7.dnum=\"1\";s7.usid=\"06e0f7c65498fe1c0155c32a14ae0474\";s7.oname=\"\\u6838\\u5FC3\\u7F51\\u7EF4\\u62A4\\u5BA4\";s7.spusname=\"\\u53F6\\u4F1A\\u6807\";s7.etime=\"2020-06-04\";s7.sptime=null;s7.rk=null;s7.id=\"0000000071b0523801727d4f55510302\";s7.spst2=null;\n" +
                "s8.res=\"\\u5BB6\\u91CC\\u6709\\u4E8B\";s8.st=\"1\";s8.rtime=\"2020-06-03\";s8.ydnum=\"5\";s8.usname=\"\\u5434\\u7545\";s8.spusid=\"139\";s8.stime=\"2020-06-04\";s8.dnum=\"2\";s8.usid=\"8080808063ddab6f016449c304e5007f\";s8.oname=\"\\u653F\\u4F01\\u5BA2\\u6237\\u652F\\u6491\\u4E2D\\u5FC3\";s8.spusname=\"\\u738B\\u5251\\u658C\";s8.etime=\"2020-06-05\";s8.sptime=\"2020-06-12 15:59:46\";s8.rk=null;s8.id=\"0000000071b0523801727916e61c02fd\";s8.spst2=\"\\u540C\\u610F\";\n" +
                "s9.res=\"\\u8BF7\\u5047\\u56DE\\u5BB6\\u529E\\u5A5A\\u793C\\uFF0C\\u8BF7\\u50476\\u5929\\uFF0C\\u5206\\u522B\\u662F6\\u670819\\uFF0C22-24\\uFF0C28-29\\uFF0C\\u5E74\\u4F11\\u5047\\u4E0E5\\u6708\\u4E24\\u5929\\u8C03\\u4F11\\u4E00\\u8D77\\u4F7F\\u7528\";s9.st=\"1\";s9.rtime=\"2020-06-03\";s9.ydnum=\"5\";s9.usname=\"\\u59DC\\u6D2A\\u6CE2\";s9.spusid=\"06e0f7c6518f5ba8015232a45cba01fd\";s9.stime=\"2020-06-19\";s9.dnum=\"5\";s9.usid=\"000000006a1018c8016a6c1c793b00ab\";s9.oname=\"\\u4E91\\u7EF4\\u62A4\\u5BA4\";s9.spusname=\"\\u865E\\u5251\\u950B\";s9.etime=\"2020-06-29\";s9.sptime=\"2020-06-03 14:38:59\";s9.rk=null;s9.id=\"0000000071b05238017278b1b27301d9\";s9.spst2=\"\\u540C\\u610F\";\n" +
                "s10.res=\"5\\u670829\\u53F7\\u8BF7\\u5E74\\u4F11\\u5047\\u4E00\\u5929\";s10.st=\"1\";s10.rtime=\"2020-05-29\";s10.ydnum=\"5\";s10.usname=\"\\u4E07\\u5B50\\u8C6A\";s10.spusid=\"06e0f7c6518f5ba8015232a45cba01fd\";s10.stime=\"2020-05-29\";s10.dnum=\"1\";s10.usid=\"06e0f7c65498fe1c0155b4170a4d0442\";s10.oname=\"\\u4E91\\u7EF4\\u62A4\\u5BA4\";s10.spusname=\"\\u865E\\u5251\\u950B\";s10.etime=\"2020-05-29\";s10.sptime=\"2020-05-29 09:59:40\";s10.rk=null;s10.id=\"0000000071b0523801725d9541e70191\";s10.spst2=\"\\u540C\\u610F\";\n" +
                "s11.res=\"6\\u67081\\u65E5\\u8BF7\\u5E74\\u5047\\u4E00\\u5929\";s11.st=\"1\";s11.rtime=\"2020-05-29\";s11.ydnum=\"5\";s11.usname=\"\\u5B59\\u4EC1\\u6770\";s11.spusid=\"06e0f7c6518f5ba8015232a45cba01fd\";s11.stime=\"2020-06-01\";s11.dnum=\"1\";s11.usid=\"06e0f7c648638f97014a7473af03048b\";s11.oname=\"\\u4E91\\u7EF4\\u62A4\\u5BA4\";s11.spusname=\"\\u865E\\u5251\\u950B\";s11.etime=\"2020-06-01\";s11.sptime=\"2020-05-29 16:52:54\";s11.rk=null;s11.id=\"0000000071b0523801725f79bfb101bf\";s11.spst2=\"\\u540C\\u610F\";\n" +
                "s12.res=\"\\u5BB6\\u91CC\\u6709\\u4E8B\\uFF0C\\u8BF7\\u5E74\\u4F11\\u4E00\\u5929\";s12.st=\"1\";s12.rtime=\"2020-05-28\";s12.ydnum=\"15\";s12.usname=\"\\u65B9\\u6F46\";s12.spusid=\"7\";s12.stime=\"2020-06-01\";s12.dnum=\"1\";s12.usid=\"8\";s12.oname=\"\\u7EFC\\u5408\\u5206\\u6790\\u5BA4\";s12.spusname=\"\\u5F20\\u5EFA\\u6210\";s12.etime=\"2020-06-01\";s12.sptime=null;s12.rk=null;s12.id=\"0000000071b0523801725892c105018b\";s12.spst2=null;\n" +
                "s13.res=\"5\\u670828\\u65E5\\u4E0A\\u5348\\u8BF7\\u5E74\\u4F11\";s13.st=\"1\";s13.rtime=\"2020-05-27\";s13.ydnum=\"10\";s13.usname=\"\\u738B\\u82B3\";s13.spusid=\"7\";s13.stime=\"2020-05-28\";s13.dnum=\"1\";s13.usid=\"23\";s13.oname=\"\\u7F51\\u7EDC\\u76D1\\u63A7\\u5BA4\";s13.spusname=\"\\u5F20\\u5EFA\\u6210\";s13.etime=\"2020-05-28\";s13.sptime=null;s13.rk=null;s13.id=\"0000000071b05238017253b35c880185\";s13.spst2=null;\n" +
                "s14.res=\"\\u5E74\\u4F110.5\\u5929\\uFF082020\\u5E74\\u4F11\\u5047\\uFF09\";s14.st=\"1\";s14.rtime=\"2020-05-27\";s14.ydnum=\"15\";s14.usname=\"\\u9F99\\u4E00\\u5DDD\";s14.spusid=\"06e0f7c6518f5ba8015232a45cba01fd\";s14.stime=\"2020-05-27\";s14.dnum=\"1\";s14.usid=\"88\";s14.oname=\"\\u4E91\\u7EF4\\u62A4\\u5BA4\";s14.spusname=\"\\u865E\\u5251\\u950B\";s14.etime=\"2020-05-28\";s14.sptime=\"2020-05-27 10:09:56\";s14.rk=null;s14.id=\"0000000071b05238017253923c960184\";s14.spst2=\"\\u540C\\u610F\";\n" +
                "s15.res=\"\\u5E74\\u4F11\\u50472\\u5929\";s15.st=\"1\";s15.rtime=\"2020-05-25\";s15.ydnum=\"10\";s15.usname=\"\\u9648\\u5C0F\\u7EA2\";s15.spusid=\"139\";s15.stime=\"2020-05-26\";s15.dnum=\"2\";s15.usid=\"144\";s15.oname=\"\\u653F\\u4F01\\u5BA2\\u6237\\u652F\\u6491\\u4E2D\\u5FC3\";s15.spusname=\"\\u738B\\u5251\\u658C\";s15.etime=\"2020-05-27\";s15.sptime=\"2020-05-25 15:32:03\";s15.rk=null;s15.id=\"0000000071b0523801724a89d0530164\";s15.spst2=\"\\u540C\\u610F\";\n" +
                "dwr.engine._remoteHandleCallback('1','0',{pS:15,tP:138,bN:1,list:s0,cP:1,aN:2068});";
        //正则表达式取出{}中的内容
//        String reg = "(\\{([^\\}]+)\\})";
        String reg = "(?<=(dwr.engine._remoteHandleCallback\\())(.+?)(?=\\))";

        String res = PatternUtil.regularFindWrite(str,reg);
        System.out.println(res);








//        String str1 = "abc3443abcfgjhgabcgfjabc";
//        String reg1 = "abc(.*?)abc";
//        System.out.println(getSubUtil(str1,reg1));
//        System.out.println(getSubUtilSimple(str1, reg1));



        //正则去除{}
//        String regular ="(\\{|})";
//        System.out.println(res.replaceAll(regular,""));

        /*String test = "\\u8FC7\\u671F\\uFF0C\\u8BF7\\u91CD\\u65B0\\u767B\\u5165\\uFF01";
        System.out.println(PatternUtil.decodeUnicode(test));*/













    }









}
