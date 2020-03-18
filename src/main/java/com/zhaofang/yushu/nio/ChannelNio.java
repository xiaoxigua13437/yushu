package com.zhaofang.yushu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ChannelNio {


    /**
     * NIO方式操作本地文件
     */
    public void getFile() {
        //通过FileInputstream拿到输入FileChannel。
        FileChannel in;
        //通过FileOutPutStream拿到输出FileChannel
        FileChannel out;

            try {
                in = new FileInputStream("E:\\huzhou.txt").getChannel();
                out = new FileOutputStream("F:\\huzhou.txt").getChannel();
                //创建一个字节缓冲器,用于传输数据
                ByteBuffer buf = ByteBuffer.allocate(1024);

                //相当于缓冲器的开关，只有调用该方法，缓冲器里面的数据才能被写入到输出Channel.
                while (in.read(buf) != -1) {
                    buf.flip();
                    out.write(buf);
                    buf.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    /**
     * nio 使用0拷贝实现操作本地文件
     */
    public void ZeroCopy(){
        FileChannel in;
        FileChannel out;

        try {
            in = new FileInputStream("E:\\huzhou.txt").getChannel();
            out = new FileOutputStream("E:\\huzhou1.txt").getChannel();
            in.transferTo(0,in.size(),out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void SuperFileRead(){
        FileChannel in;
        try {
            in = new FileInputStream("").getChannel();
            //通过map()方法产生一个缓冲器
            MappedByteBuffer mappedByteBuffer = in.map(FileChannel.MapMode.READ_ONLY,0,in.size());
            if (mappedByteBuffer != null){
                CharBuffer charBuffer = Charset.forName("utf-8").decode(mappedByteBuffer);
                System.out.println(charBuffer.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }





















    }





}
