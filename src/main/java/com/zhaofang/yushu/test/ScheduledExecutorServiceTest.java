package com.zhaofang.yushu.test;


import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;
/**
 * @author yushu
 * @create 2020-06-15 11:00
 */
public class ScheduledExecutorServiceTest {


    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    //用来存储时间变量
    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * 设置了 ScheduledExecutorService ，在 1 小时内每 10 秒钟蜂鸣一次
     */
    private void beerForHour(){
        final Runnable beeper = new Runnable() {
            @Override
            public void run() {
                System.out.println("beep");
            }
        };
        /**
         * scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
         * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，
         * 然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推。
         */
        final ScheduledFuture<?> beeperHandle =
                scheduledExecutorService.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
                //创建并执行在给定延迟后启用的一次性操作。
//                scheduledExecutorService.schedule(beeper,10,SECONDS);
        scheduledExecutorService.schedule(new Runnable() {
            public void run() { beeperHandle.cancel(true); }
            }, 60 * 60, SECONDS);
        }


     public static void main(String[] args){

        new ScheduledExecutorServiceTest().beerForHour();




     }
















    }









