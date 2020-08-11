package com.zhaofang.yushu.common.httpclient;

import com.alibaba.fastjson.JSONObject;
import com.zhaofang.yushu.common.regular.PatternUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求工具类
 * @author yushu
 * @create 2020-07-21 15:23
 *
 */
public abstract class HttpUtil {

    public static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    //用来存储cookie值
    public static ThreadLocal<Map<String,Object>> saveCookieByMap = new ThreadLocal<>();


    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr,String cookie) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection conn;
        try {
            httpsValid();
            URL url = new URL(requestUrl);
            // 通过请求地址判断请求类型(http或者是https)
            if (url.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
            conn.setRequestProperty("Cookie",cookie);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            if (requestMethod.equalsIgnoreCase("GET")) {
                conn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                // 获取URLConnection对象对应的输出流
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            System.out.println(buffer.toString());
            jsonObject = JSONObject.parseObject(buffer.toString());
//             jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:", e);
        }
        return jsonObject;
    }

    /**
     * 验证https请求证书，免验证
     */
    public static void httpsValid() {
        // 创建SSLContext对象,并使用我们指定的信任管理器初始化
        TrustManager[] tm = {new MyX509TrustManager()};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new SecureRandom());
            // 获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(ssf);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 验证https请求证书，加载证书
     */
    public static void loadCertificate() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // 加载证书路径
            InputStream in = new InputStream() {

                @Override
                public int read() throws IOException {
                    return 0;
                }
            };
            Certificate ca = cf.generateCertificate(in);

            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null, null);
            keystore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keystore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            // HttpsURLConnection.setSSLSocketFactory(context.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection conn;
        try {
            httpsValid();
            // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            URL url = new URL(requestUrl);
            // 通过请求地址判断请求类型(http或者是https)
            if (url.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                // 设置域名检验
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
            // 设置连接参数
            conn.setDoOutput(true);// 允许输出流，即允许上传
            // //在android中必须将此项设置为false
            conn.setDoInput(true);// 允许输入流，即允许下载
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            if (requestMethod.equalsIgnoreCase("GET")) {
                conn.connect();
            }

            // 当有数据需要提交时
            if (outputStr != null) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 清理
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            // 转化为JSON对象
            jsonObject = JSONObject.parseObject(buffer.toString());
            // jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:", e);
        }
        return jsonObject;
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String httpRequestByString(String requestUrl, String requestMethod,
                                             String outputStr) {
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection conn;
        try {
            httpsValid();
            URL url = new URL(requestUrl);
            // 通过请求地址判断请求类型(http或者是https)
            if (url.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                // 设置域名检验
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
            conn.setRequestProperty("Cookie",HttpUtil.getCookieByThreadLocal("cookies").toString());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            if (requestMethod.equalsIgnoreCase("GET")) {
                conn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            conn.disconnect();
        } catch (ConnectException ce) {
            log.error("server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:", e);
        }
        return buffer.toString();
    }

    /**
     * 发起soap协议的http请求并获取结果
     *
     * @param requestUrl 请求地址 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String soapRequest(String requestUrl, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            int timeout = 10000;
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            httpUrlConn.setRequestProperty("soapaction", "");
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setConnectTimeout(timeout);
            httpUrlConn.setReadTimeout(timeout);

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            log.info("soap协议请求");
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            log.error("server connection timed out.");
        } catch (Exception e) {
            log.error("soap request error:", e);
        }
        return buffer.toString();
    }



    /**
     * post方式请求获取cookie
     *
     * @param url 链接
     * @param params 请求参数
     * @param headers 请求头部参数
     * @param charSet 编码
     * @return cookie
     */
    public static String getCookieByPost(String url, Map<String ,String> params, Map<String ,String>headers, String charSet) {
        try {
            URL url1=new URL(url);
            HttpURLConnection conn=(HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("POST");
            //输入参数
            conn.setDoOutput(true);
            //不使用缓存
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(60*1000);
            for (String header : headers.keySet()) {
                conn.setRequestProperty(header, headers.get(header));
            }
            conn.connect();
            if(params!=null && params.keySet().size()>0) {
                StringBuilder sb=new StringBuilder();
                for (String key : params.keySet()) {
                    sb.append(key+"="+ URLEncoder.encode(params.get(key),charSet)+"&");
                }
                String param=sb.delete(sb.length()-1, sb.length()).toString();
                DataOutputStream out=new DataOutputStream(conn.getOutputStream());
                out.writeBytes(param);
                out.flush();
                out.close();
            }
            String cookie="";
            Map<String, List<String>> respone_headers=conn.getHeaderFields();
            for (String key : respone_headers.keySet()) {
            //System.err.println(respone_headers.get(key));
                if(key!=null &&key.equals("Set-Cookie")) {
                    List<String> string = respone_headers.get(key);
                    StringBuilder builder = new StringBuilder();
                    for (String str : string) {
                        System.err.println(str);
                        builder.append(str).toString();
                    }
                    cookie=builder.toString();
                }
            }
            conn.disconnect();
            return cookie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



    /**
     * 发起http请求获取cookie并存储到线程变量
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String getCookieByPost(String requestUrl, String requestMethod, String outputStr) {

        StringBuffer buffer = new StringBuffer();
        String cookie="";
        HttpURLConnection conn;
        try {
            httpsValid();
            URL url = new URL(requestUrl);
            // 通过请求地址判断请求类型(http或者是https)
            if (url.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                // 设置域名检验
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            if (requestMethod.equalsIgnoreCase("GET")) {
                conn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String reStr = null;
            while ((reStr = bufferedReader.readLine()) != null) {
                buffer.append(reStr);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            Map<String, List<String>> responseHeaders=conn.getHeaderFields();
            for (String key : responseHeaders.keySet()) {
                //System.err.println(responseHeaders.get(key));
                if(key!=null &&key.equals("Set-Cookie")) {
                    List<String> string = responseHeaders.get(key);
                    StringBuilder builder = new StringBuilder();
                    for (String str : string) {
                        builder.append(str).toString();
                    }
                    cookie=builder.toString();
                    //存储到线程变量
                    HttpUtil.addCookieToThreadLocal("cookies",cookie);
                }
            }
            conn.disconnect();

        } catch (ConnectException ce) {
            log.error("server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:", e);
        }
        return buffer.toString();

    }


    /**
     * 添加值到线程变量
     * @param key
     * @param value
     */
    public static void addCookieToThreadLocal(String key,String value){
        Map<String,Object> map = saveCookieByMap.get();
        if (map == null){
            map = new HashMap<>();
        }
        map.put(key,value);
        saveCookieByMap.set(map);

    }

    /**
     * 获取线程变量的值
     * @param key
     * @return
     */
    public static Object getCookieByThreadLocal(String key){
        return saveCookieByMap.get().get(key);
    }


    /**
     * 销毁线程变量
     */
    public static void destroy(){
        saveCookieByMap.remove();
    }








    /*
     * unicode编码转中文
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


    public static void main(String[] args) {

        String rsUrl = "http://192.168.83.55:8080/noc/dwr/call/plaincall/Jes.save.dwr\n";
        String reUrl = "http://192.168.83.55:8080/noc/dwr/call/plaincall/Jes.page.dwr\n";
        String requestMethod = "POST";
        String requestStr = "callCount=1\n" +
                "page=/noc/jesacl/html/login.html\n" +
                "httpSessionId=\n" +
                "scriptSessionId=59BD7C82CD3F22D3AF7C321A48F7429F527\n" +
                "c0-scriptName=Jes\n" +
                "c0-methodName=save\n" +
                "c0-id=0\n" +
                "c0-param0=string:login%40acl\n" +
                "c0-e1=string:\n" +
                "c0-e2=string:123456\n" +
                "c0-e3=string:\n" +
                "c0-e4=number:0\n" +
                "c0-param1=Object_Object:{loginid:reference:c0-e1, password:reference:c0-e2, code_input:reference:c0-e3, se:reference:c0-e4}\n" +
                "batchId=7";


        String responseStr = "callCount=1\n" +
                "page=/noc/noc/pub/nocholiday.html\n" +
                "httpSessionId=\n" +
                "scriptSessionId=C6BB6F9409D73C784FA2D4702AC5EFA6179\n" +
                "c0-scriptName=Jes\n" +
                "c0-methodName=page\n" +
                "c0-id=0\n" +
                "c0-e1=string:\n" +
                "c0-e2=string:\n" +
                "c0-e3=string:holiday_query_sql%40noc\n" +
                "c0-e4=null:null\n" +
                "c0-e5=null:null\n" +
                "c0-param0=Object_Object:{usname:reference:c0-e1, oname:reference:c0-e2, sql:reference:c0-e3, key:reference:c0-e4, se:reference:c0-e5}\n" +
                "c0-param1=number:1\n" +
                "c0-param2=number:15\n" +
                "batchId=1";
         HttpUtil.getCookieByPost(rsUrl,requestMethod,requestStr);

//         String result = HttpUtil.httpRequestByString(reUrl,requestMethod,responseStr);
         //正则匹配{}中的内容
//         String reg = "(\\{([^\\}]+)\\})";

//        String regex = "(?<=(dwr.engine._remoteHandleCallback\\())(.+?)(?=\\))";

        //去除{}
//       String regular ="(\\{|})";
//         String content = PatternUtil.regularFindWrite(result,reg);
//
//         content = content.replaceAll("list.+?,", "");
//         System.out.println(content);
//
//         JSONObject jsonObject = JSONObject.parseObject(content);





    }

}
