package com.zhaofang.yushu.common.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.util.Map;

/**
 *
 * @author yushu
 * @create 2020-09-12 14:26
 *
 * @see不建议直接调用通过HttpUtil工具类去调用
 */
public class HttpClientUtil {


    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 请求超时时间设置
     */
    private final static int SOCKET_TIMEOUT = 20000;
    private final static int CONNECT_TIMEOUT = 20000;

    /**
     * 请求提交类型
     */
    public enum CONTENT_TYPE {
        APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
        APPLICATION_JSON("application/json"),
        TEXT_XML("text/xml");
        private String value;

        CONTENT_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * get请求
     * @param url 请求路径
     * @return
     */
    public static String getRequest(String url) throws Exception {
        return getRequest(url, null);
    }

    /**
     * get请求
     * @param url 请求路径
     * @param headersParams  请求头参数
     * @return
     * @throws Exception
     */
    public static String getRequest(String url, Map<String, String> headersParams) throws Exception {
        CloseableHttpClient httpClient = SSLUtils.createSSLInsecureClient();
        return doGet(httpClient, url, headersParams);
    }

    /**
     * post请求
     * @param url  请求路径
     * @param dataBody  传递参数
     * @return
     */
    public static String postRequest(String url, String dataBody) throws Exception {
        return postRequest(url, dataBody, null, null);
    }


    /**
     * post请求，指定contentType
     * @param url  请求路径
     * @param dataBody  传递参数
     * @param contentType   请求类型（不指定传null）
     * @param headersParams 自定义请求头参数（不需要传null）
     * @return
     */
    public static String postRequest(String url, String dataBody, CONTENT_TYPE contentType,
                                     Map<String, String> headersParams)
            throws Exception {
        CloseableHttpClient httpClient = SSLUtils.createSSLInsecureClient();
        return doPost(httpClient, url, dataBody, contentType.getValue(), headersParams);
    }


    /**
     * get请求
     * @param httpClient
     * @param url
     * @param headersParams
     * @return
     * @throws Exception
     */
    private static String doGet(CloseableHttpClient httpClient, String url,
                                Map<String, String> headersParams) throws Exception {
        HttpGet get = new HttpGet(new URI(url));
        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).build();//设置请求和传输超时时间
        get.setConfig(requestConfig);

        if (headersParams != null) {
            for (Map.Entry<String, String> enks : headersParams.entrySet()) {
                get.addHeader(enks.getKey(), enks.getValue());
            }
        }

        HttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }

    /**
     * post请求
     * @param httpClient
     * @param url
     * @param dataBody
     * @param contentType
     * @param headersParams 自定义请求头参数
     * @return
     * @throws Exception
     */
    private static String doPost(CloseableHttpClient httpClient, String url, String dataBody,
                                 String contentType, Map<String, String> headersParams) throws Exception {

        if (dataBody == null) {
            dataBody = "";
        }

        HttpPost httpPost = new HttpPost(new URI(url));
        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);

        if (contentType != null) {
            Header header = new BasicHeader("Content-Type", contentType);
            httpPost.addHeader(header);
        }
        //设置自定义请求头参数
        if (headersParams != null && headersParams.size() > 0) {
            for (Map.Entry<String, String> enks : headersParams.entrySet()) {
                httpPost.addHeader(enks.getKey(), enks.getValue());
            }
        }

        httpPost.setEntity(new StringEntity(dataBody, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String responseContent = "";
        if (null != entity) {
            responseContent = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        }
        return responseContent;
    }
}
