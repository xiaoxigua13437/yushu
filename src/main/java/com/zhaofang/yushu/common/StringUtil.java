package com.zhaofang.yushu.common;


/**
 * StringUtil工具类
 *
 * @author yushu
 * @create 2020-08-08 15:12
 */
public class StringUtil {

    public static boolean isEmpty(Object value){
        return (value == null || "".equals(value));
    }


    public static boolean isNotEmpty(Object value){
        return (!isEmpty(value));
    }








    /**
     * 对数据进行交换
     *
     * @param arr
     * @param i
     * @param j
     * @return
     */
    public static int[] swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }



    public static void main(String[] args){
        int[] a ={1,2};
        int[] c = StringUtil.swap(a,0,1);
        System.out.println(c[0] + "\t" + c[1]);
    }


}
