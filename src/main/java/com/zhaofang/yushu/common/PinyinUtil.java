package com.zhaofang.yushu.common;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音转换工具类
 *
 * @author yushu
 * @create 2020-08-21 10:18
 */
public class PinyinUtil {

    private static HanyuPinyinOutputFormat PINYIN_FORMAT;

    static {
        PINYIN_FORMAT = new HanyuPinyinOutputFormat();
        PINYIN_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        PINYIN_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 字符串转拼音
     *
     * @param input
     * @return
     */
    public static String toPinyin(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c <= 255) {
                sb.append(c);
            } else {
                String pinyin = null;
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, PINYIN_FORMAT);
                    pinyin = pinyinArray[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                } catch (NullPointerException e) {
                    // 如果是日文，可能抛出该异常
                }
                if (pinyin != null) {
                    sb.append(pinyin);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 字符串转换拼音
     *
     * @param str
     * @return
     */
    public static String getPingYin(String str) {
        char[] t1;
        t1 = str.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += java.lang.Character.toString(t1[i]);
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }



    /*public static void main(String[] args){

        String str = "玉书";
        String pinyin = PinyinUtil.getPingYin(str);
        System.out.println("pinyin:{"+pinyin+"}");

    }*/




}
