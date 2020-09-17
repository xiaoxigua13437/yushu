package com.zhaofang.yushu.common.httpclient;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;

/**
 * okHttp工具类
 *
 * @author yushu
 * @create 2020-06-10
 */
public class OkHttpUtil {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //超时时长
    public final static int CONNECT_TIMEOUT = 10000;

    private static OkHttpClient okHttpClient;

    static {
        InputStream[] certificates = null;
        InputStream bksFile = null;
//        HttpsUtils.SSLParams sslParams =HttpsUtils.getSslSocketFactory(null, null, null)
        try {
            certificates = new InputStream[]{new ClassPathResource("server.cer").getInputStream()};
            bksFile = new ClassPathResource("tomcat.keystore").getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile,
                bksFile == null ? null : "123456");
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
    }

    /**
     * 同步get
     */
    public static String get(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();

        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 异步get请求
     */
    public static void get(String url, Callback responseCallback) throws Exception {
        Request request = new Request.Builder().url(url).build();

        enqueue(request, responseCallback);
    }

    /**
     * 同步post json数据
     */
    public static String postWithHeaders(String url, String json, Map<String, String> headers)
            throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url).post(body);
        for (String key : headers.keySet()) {
            builder.addHeader(key, headers.get(key));
        }
        Request request = builder.build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 同步post json数据
     */
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * 异步post json
     */
    public static void post(String url, String json, Callback responseCallback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        if (responseCallback != null) {
            enqueue(request, responseCallback);
        } else {
            enqueue(request);
        }
    }

    /**
     * 通用同步请求。
     */
    public static Response execute(Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 通用异步请求
     */
    public static void enqueue(Request request, Callback responseCallback) {
        okHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     */
    public static void enqueue(Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    public static void main(String[] args) throws IOException {
        JSONObject json = new JSONObject();
        json.put("username", "admin");
        String str = post("http://192.168.12.81:8188/system_logout", json.toJSONString());
        System.out.println(str);
    }

}
