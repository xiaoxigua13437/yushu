package com.zhaofang.yushu.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;


/**
 * 拦截请求，添加自定义请求逻辑(获取请求参数)
 * @author yushu
 * @create 2020-06-16 16:05
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final static Logger logger= LoggerFactory.getLogger(BodyReaderHttpServletRequestWrapper.class);

    //最大请求报文数
    private final static Integer MAX_CONTEXT_LENHTH = 1000;

    private final byte[] body;

    /**
     * 构造一个封装给定请求的请求对象
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     *
     * @throws java.lang.IllegalArgumentException if the request is null
     */
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        //获取请求参数值
        this.body = getBodyString(request).getBytes(Charset.forName("utf-8"));
        //将值写入线程变量中
        try {
            String parameters = new String(body,"utf-8");
            if (logger.isDebugEnabled()){
                logger.debug("请求参数{}" + parameters);
            }
            //获取请求参数
            BodyReaderFilter.addValueToMyThreadLocal("requestParameters",parameters);

        } catch (UnsupportedEncodingException e) {
            BodyReaderFilter.addValueToMyThreadLocal("requestParameters","{}");
        }

    }


    /**
     * 继承实现getReader()重写逻辑，自定义的HttpServletRequestWrapper将原始的HttpServletRequest对象进行再次封装
     *
     * @throws IOException
     * @return
     */
    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    /**
     * 将body体中的字符串转换为字节流
     *
     * @throws IOException
     * @return
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }




    /**
     * 获取httpServletRequest流数据方法
     *
     * @param request
     * @return
     */
    private static String getBodyString(ServletRequest request){
        StringBuffer strReturnVal = new StringBuffer("");
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(request.getInputStream(), "UTF-8");
            while (true) {
                char[] c = new char[1024];
                int ilen = isr.read(c);
                if (ilen <= 0) {
                    break;
                }
                strReturnVal = strReturnVal.append(c, 0, ilen);
                if(strReturnVal.length() > MAX_CONTEXT_LENHTH){
                    throw new Exception("报文超过最大限制");
                }
            }
            return strReturnVal.toString();
        } catch (Exception e) {
            logger.error("读取http请求内容出错" , e);
            return null;
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    logger.error("关闭request输入流出错" , e);
                }
            }
        }
    }





}