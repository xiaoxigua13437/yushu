package com.zhaofang.yushu;

//import com.zhaofang.yushu.common.BackProperties;
import com.zhaofang.yushu.entity.User;
import com.zhaofang.yushu.service.TokenService;
import javafx.application.Application;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
//import java.time.Instant;
//import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@SpringBootTest
class YushuApplicationTests {


    //模拟数据库存储
    private static ThreadLocal<Map<String,String>>  threadLocal = new ThreadLocal<Map<String, String>>();

    @Autowired
//    private BackProperties backProperties;

    @Test
    void contextLoads() {
    }

    /**
     * excel导出数据
     */
    @Test
    public void test(){

        User user = new User();
        user.setUserId(1);
        user.setUserName("hhhh");
        user.setUserSex("男");

        String[] titles = {"编号","姓名","性别"};

        //1.创建文件对象   创建HSSFWorkbook只能够写出为xls格式的Excel//        
        // 要写出 xlsx 需要创建为 XSSFWorkbook 两种Api基本使用方式一样

        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建表对象
        HSSFSheet sheet = workbook.createSheet("users");
        //3.创建标题栏（第一行）  参数为行下标  行下标从0开始
        HSSFRow titleRow = sheet.createRow(0);
        //4.在标题栏中写入数据
        for (int i = 0; i < titles.length; i++){
            //创建单元格
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        //写入用户数据
        //5.创建行 如果是用户数据的集合 需要遍历
        HSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue(user.getUserId());
        row.createCell(1).setCellValue(user.getUserName());
        row.createCell(2).setCellValue(user.getUserSex());

        HSSFRow row1;
        for (int r = 2 ;r<5;r++){
            //创建一个行对象
            row1 = sheet.createRow(r);
            row1.createCell(0).setCellValue(user.getUserId());
            row1.createCell(1).setCellValue(user.getUserName());
            row1.createCell(2).setCellValue(user.getUserSex());
        }

        try {
            workbook.write(new FileOutputStream("E:\\hhh.xls"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void test2() throws IOException {
        //通过流读取Excel文件
        FileInputStream inputStream = new FileInputStream("E:\\hhh.xls");
        //通过poi解析流 HSSFWorkbook 处理流得到的对象中 就封装了Excel文件所有的数据
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        //从文件中获取表对象
        HSSFSheet sheet = workbook.getSheetAt(0);
        //从表中获取到行数据  从第二行开始 到 最后一行  getLastRowNum() 获取最后一行的下标
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i < lastRowNum; i++){
            //通过下标获取行
            HSSFRow row = sheet.getRow(i);
            //再从行中获取数据

            double id = row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            String sex = row.getCell(2).getStringCellValue();
            //封装数据
            User user = new User();
            user.setUserId((int) id);
            user.setUserName(name);
            user.setUserSex(sex);
            //将对象添加数据库
            System.out.println(user.toString());

        }

    }
            @Test
            public void test3(){
                //通过FileInputstream拿到输入FileChannel。
                FileChannel in;
                //通过FileOutPutStream拿到输出FileChannel
                FileChannel out;
                {
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
            }

            @Test
            public void test4(){
                FileChannel in;
                FileChannel out;

                try {
                    in = new FileInputStream("E:\\huzhou.txt").getChannel();
                    out = new FileOutputStream("E:\\huzhou1.txt").getChannel();
                    in.transferTo(0,in.size(),out);
                    //或者
                    //out.transferFrom(in,0,in.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Test
            public void test5(){
                FileChannel in;

                try {
                    in = new FileInputStream("E:\\boce.cap").getChannel();
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

            @Test
            public void test6(){

                Integer c = 15;
                Date date = new Date();
                Long startTime = Long.valueOf(date.getTime());
                System.out.println("startTime:"+startTime);
                Long endTime = Long.valueOf(date.getTime() -c.intValue() * 60 * 1000L);

                Long tiem = startTime -15 * 60 * 1000L;
                System.out.println("tiem:" +tiem);
                System.out.println("endTime:"+endTime);

            }

            @Test
            public void testAssert(){
//                int b = 1;
//                Assert.isTrue(b !=1,"b不等于1");
//                String a = "aaa";
//                Assert.isNull(a,"a的值为空");

//                System.out.println(Instant.now().atZone(ZoneId.systemDefault()));
//                StringBuilder builder = new StringBuilder();
//                Random random = new Random();
//                for (int i = 0; i < 10; i++){
//                    builder.append(random.nextInt(6));
//                }



//                int a = 2;
//                int b = a++ << ++a + ++a;
//                System.out.println(b);


                //查找指定字符或者字符串在字符串中第一次出现的位置的索引,未找到返回-1
//                String a = "abc";
//                System.out.println(a.indexOf("a",2));

            }










}
