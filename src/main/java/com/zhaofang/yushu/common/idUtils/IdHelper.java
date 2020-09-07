package com.zhaofang.yushu.common.idUtils;

/**
 * id生成器对外接口
 *
 * @author yushu
 * @date 2017/12/18
 **/
public class IdHelper
{
    private volatile static SnowflakeIdWorker worker;

    private static SnowflakeIdWorker init()
    {
        if (worker == null)
        {
            synchronized (IdHelper.class){

                if(worker==null){
                    worker = new SnowflakeIdWorker(0, 0);
                }

            }
        }
        return worker;
    }

    public static long getId()
    {
        return init().nextId();
    }
}
