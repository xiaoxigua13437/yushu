package com.zhaofang.yushu.common.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yushu
 * @create 2020-06-16 15:08
 */
public abstract class HttpClientHelper {


    static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    static final String HEADER_KEY_ENCODING = "Accept-Encoding";
    static final String HEADER_VALUE_ENCODING = "gzip,deflate";
    static final String HEADER_KEY_AGENT = "User-Agent";
    static final String HEADER_VALUE_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)";
    private static Logger log = LoggerFactory.getLogger(HttpClientHelper.class);
    static final String DEFAULT_RESPONSE_STRING = "HTTP_REQUEST_ERROR";

    private HttpClientHelper() {
    }

    public static byte[] sendGetRequestForImageStream(String requestURL) {
        HttpRequestBase httpRequest = new HttpGet(requestURL);
        httpRequest.addHeader(HEADER_KEY_ENCODING, HEADER_VALUE_ENCODING);
        httpRequest.addHeader(HEADER_KEY_AGENT, HEADER_VALUE_AGENT);
        CloseableHttpResponse response = sendRequest(httpRequest);
        if (response != null) {
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity responseEntity = response.getEntity();
                log.info(Long.toString(responseEntity.getContentLength()));
                byte[] buffer = new byte[4096];
                int n = 0;
                try (
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        InputStream is = responseEntity.getContent();
                ) {
                    while (-1 != (n = is.read(buffer))) {
                        output.write(buffer, 0, n);
                    }
                    return output.toByteArray();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    httpRequest.releaseConnection();
                    try {
                        if (null != response) {
                            response.close();
                        }
                    } catch (IOException e) {
                        log.error("sendGetRequest Exception##### " + requestURL, e);
                    }
                }
            } else {
                log.info(
                        "sendGetRequestForImageStream status code:" + statusCode + "," + httpRequest.getURI()
                                .getPath());
            }
        }
        return new byte[]{};

    }

    public static String sendGetRequest(String requestURL) {
        return sendGetRequest(requestURL, null);
    }

    /**
     * get请求
     */
    public static String sendGetRequest(String requestURL, Map<String, String> headersExtra) {
        HttpRequestBase httpRequest = new HttpGet(requestURL);
        httpRequest.addHeader(HEADER_KEY_ENCODING, HEADER_VALUE_ENCODING);
        httpRequest.addHeader(HEADER_KEY_AGENT, HEADER_VALUE_AGENT);
        if (headersExtra != null) {
            for (Map.Entry<String, String> enks : headersExtra.entrySet()) {
                httpRequest.addHeader(enks.getKey(), enks.getValue());
            }
        }
        CloseableHttpResponse response = sendRequest(httpRequest);
        String decompressResponseString = DEFAULT_RESPONSE_STRING;
        int statusCode = 0;
        if (response == null) {
            //nothing
        } else {
            StatusLine statusLine = response.getStatusLine();
            statusCode = statusLine.getStatusCode();
        }
        if (HttpStatus.SC_OK == statusCode) {
            // 打印解压后的返回信息
            HttpEntity responseEntity = response.getEntity();
            log.info(responseEntity.getContentLength() + "");
            try {
                log.info(responseEntity.getContent() == null ? "nothing"
                        : responseEntity.getContent().toString());
            } catch (Exception e) {
                log.error("sendGetRequest Exception##### " + requestURL, e);
            }
            Header[] headers = response.getHeaders("Content-Encoding");
            for (Header header : headers) {
                if (0 == header.getValue().compareToIgnoreCase("gzip")) {
                    responseEntity = new GzipDecompressingEntity(responseEntity);
                    break;
                }
            }
            try {
                decompressResponseString = EntityUtils.toString(responseEntity);
            } catch (Exception e) {
                log.error("sendGetRequest Exception##### " + requestURL, e);
            } finally {
                httpRequest.releaseConnection();
                try {
                    if (null != response) {
                        response.close();
                    }
                } catch (IOException e) {
                    log.error("sendGetRequest Exception##### " + requestURL, e);
                }
            }
        } else {
            log.info("sendGetRequest status code:" + statusCode + "," + httpRequest.getURI().getPath());
        }
        return decompressResponseString;

    }

    /**
     * post请求,使用map<String,String>
     */
    public static String sendPostRequest(String requestURL, Map<String, String> postParams) {
        HttpPost httpRequest = new HttpPost(requestURL);
        httpRequest.addHeader(HEADER_KEY_ENCODING, HEADER_VALUE_ENCODING);
        httpRequest.addHeader(HEADER_KEY_AGENT, HEADER_VALUE_AGENT);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000)
                .setConnectTimeout(1000).build();
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : postParams.entrySet()) {
            paramsList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        try {
            // 模拟表单请求，适合浏览器发送的请求
            httpRequest.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("sendPostRequest Exception##### " + requestURL, e);
        }
        httpRequest.setConfig(requestConfig);

        CloseableHttpResponse response = sendRequest(httpRequest);
        String decompressResponseString = DEFAULT_RESPONSE_STRING;
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            // 打印解压后的返回信息
            HttpEntity responseEntity = response.getEntity();
            log.info(responseEntity.getContentLength() + "");
            Header[] headers = response.getHeaders("Content-Encoding");
            for (Header header : headers) {
                if (0 == header.getValue().compareToIgnoreCase("gzip")) {
                    responseEntity = new GzipDecompressingEntity(responseEntity);
                    break;
                }
            }
            try {
                decompressResponseString = EntityUtils.toString(responseEntity);
            } catch (ParseException e) {
                e.printStackTrace();
                log.error("sendPostRequest Exception##### " + requestURL, e);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("sendPostRequest Exception##### " + requestURL, e);
            } finally {
                httpRequest.releaseConnection();
                try {
                    if (null != response) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("sendPostRequest Exception##### " + requestURL, e);
                }
            }

        } else {
            log.info("sendRequest status code:" + statusCode + "," + httpRequest.getURI().getPath());
        }
        return decompressResponseString;

    }

    /**
     * post请求,使用map<String,String>，适合json数据请求
     */
    public static String sendPostJson(String requestURL, Map<String, String> postParams) {
        HttpPost httpRequest = new HttpPost(requestURL);
        // 请求参数
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
                .setConnectTimeout(2000).build();
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : postParams.entrySet()) {
            paramsList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        // 将提交的参数转化为json字符串
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(postParams),
                ContentType.APPLICATION_JSON);
        httpRequest.setEntity(stringEntity);
        httpRequest.setConfig(requestConfig);
        CloseableHttpResponse response = sendRequest(httpRequest);
        String decompressResponseString = DEFAULT_RESPONSE_STRING;
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            // 打印解压后的返回信息
            HttpEntity responseEntity = response.getEntity();
            log.info(responseEntity.getContentLength() + "");
            Header[] headers = response.getHeaders("Content-Encoding");
            for (Header header : headers) {
                if (0 == header.getValue().compareToIgnoreCase("gzip")) {
                    responseEntity = new GzipDecompressingEntity(responseEntity);
                    break;
                }
            }
            try {
                decompressResponseString = EntityUtils.toString(responseEntity);
            } catch (ParseException e) {
                log.error("sendPostRequest Exception##### " + requestURL, e);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("sendPostRequest Exception##### " + requestURL, e);
            } finally {
                httpRequest.releaseConnection();
                try {
                    if (null != response) {
                        response.close();
                    }
                } catch (IOException e) {
                    log.error("sendPostRequest Exception##### " + requestURL, e);
                }
            }

        } else {
            log.info("sendRequest status code:" + statusCode + "," + httpRequest.getURI().getPath());
        }
        return decompressResponseString;

    }

    /**
     * post请求,使用map<String,String>，适合json数据请求
     */
    public static JSONObject sendPostJson(String requestURL, JSONObject postParams) {
        HttpPost httpRequest = new HttpPost(requestURL);
        // 请求参数
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
                .setConnectTimeout(2000).build();
        // 将提交的参数转化为json字符串
        StringEntity stringEntity = new StringEntity(postParams.toString(),
                ContentType.APPLICATION_JSON);
        httpRequest.setEntity(stringEntity);
        httpRequest.setConfig(requestConfig);
        CloseableHttpResponse response = sendRequest(httpRequest);
        String decompressResponseString = DEFAULT_RESPONSE_STRING;
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            // 打印解压后的返回信息
            HttpEntity responseEntity = response.getEntity();
            log.info(responseEntity.getContentLength() + "");
            Header[] headers = response.getHeaders("Content-Encoding");
            for (Header header : headers) {
                if (0 == header.getValue().compareToIgnoreCase("gzip")) {
                    responseEntity = new GzipDecompressingEntity(responseEntity);
                    break;
                }
            }
            try {
                decompressResponseString = EntityUtils.toString(responseEntity);
            } catch (ParseException e) {
                e.printStackTrace();
                log.error("sendPostRequest Exception##### " + requestURL, e);
            } catch (IOException e) {
                log.error("sendPostRequest Exception##### " + requestURL, e);
            } finally {
                httpRequest.releaseConnection();
                try {
                    if (null != response) {
                        response.close();
                    }
                } catch (IOException e) {
                    log.error("sendPostRequest Exception##### " + requestURL, e);
                }
            }

        } else {
            log.info("sendRequest status code:" + statusCode + "," + httpRequest.getURI().getPath());
        }
        return JSONObject.parseObject(decompressResponseString);

    }

    /***
     * 将传入的xml直接发送
     */

    public static String sendPostRequestXmlDirectly(String requestURL, String xmlStr) {
        HttpPost httpRequest = new HttpPost(requestURL);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).build();
        StringEntity entity = new StringEntity(xmlStr, "UTF-8");
        entity.setContentType("application/xml");
        entity.setContentEncoding("UTF-8");
        httpRequest.setEntity(entity);
        httpRequest.setConfig(requestConfig);
        CloseableHttpResponse response = sendRequest(httpRequest);

        String decompressResponseString = DEFAULT_RESPONSE_STRING;
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            // 打印解压后的返回信息
            HttpEntity responseEntity = response.getEntity();
            try {
                decompressResponseString = EntityUtils.toString(responseEntity, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("sendPostRequestXml Exception##### " + requestURL, e);
            } finally {
                httpRequest.releaseConnection();
                try {
                    if (null != response) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("sendPostRequestXml Exception##### " + requestURL, e);
                }
            }

        } else {
            log.info("sendRequest status code:" + statusCode + "," + httpRequest.getURI().getPath());
        }
        return decompressResponseString;
    }

    /**
     * get/post请求
     */
    public static CloseableHttpResponse sendRequest(HttpRequestBase httpRequest) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {

            response = httpclient.execute(httpRequest);
        } catch (Exception e) {
            log.error("sendRequest Exception##### " + httpRequest.getURI(), e);
        }
        return response;
    }

    /**
     * get/post请求 带HTTP登录验证
     */
    public static CloseableHttpResponse sendRequest(HttpRequestBase httpRequest,
                                                    HttpClientContext httpClientcontext) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpRequest, httpClientcontext);
        } catch (Exception e) {
            log.error("sendRequest with auth Exception##### " + httpRequest.getURI(), e);
        }
        return response;
    }

    /**
     * 创建HTTP SSL验证
     */
    public static HttpClientContext createBasicAuthContext(String username, String password,
                                                           String hostname,
                                                           int port) {
        HttpHost host = new HttpHost(hostname, port, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        Credentials defaultCreds = new UsernamePasswordCredentials(username, password);
        credsProvider.setCredentials(
                new AuthScope(host.getHostName(), host.getPort(), "iMC RESTful Web Services"),
                defaultCreds);
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);
        return context;
    }

    /**
     * 基础HTTPS请求
     */
    public static String sendRequestHttps(HttpRequestBase httpRequest) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000)
                .setConnectTimeout(10000).build();
        httpRequest.setConfig(requestConfig);
        CloseableHttpClient httpclient = SSLUtils.createSSLInsecureClient();
        CloseableHttpResponse response;
        String result = null;
        try {
            response = httpclient.execute(httpRequest);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("baseHttpsRequest with auth Exception##### " + httpRequest.getURI(), e);
        }
        return result;
    }

    /**
     * 发送Https请求 ,请求格式String
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String sendPostRequestHttps(String url, String jsonStr) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity se = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        httpPost.setEntity(se);
        return sendRequestHttps(httpPost);
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String pattern = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        if (null != ip && ip.isEmpty()) {
            Matcher m = r.matcher(ip);
            if (m.find()) {
                ip = m.group(0);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // System.out.printf("request.getRemoteHost()
        // "+request.getRemoteHost());
        // System.out.printf("request.getRemoteUser()
        // "+request.getRemoteUser());
        // System.out.printf("request.getRemotePort()
        // "+request.getRemotePort());
        return ip;
    }

    /**
     * 根据参数创建url
     *
     * @param params: Map<String, String> params = new HashMap<String, String>();
     * params.put("grant_type", "client_credential"); params.put("appid", appId);//微信appid
     * params.put("secret", appSecret);//微信appsecret
     */
    public static String creatBuilderUrl(String host, String scheme, Map<String, String> params) {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            paramsList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        URI build = null;
        try {
            build = new URIBuilder().setScheme(scheme).setHost(host).setParameters(paramsList).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("creatBuilderUrl Exception##### ", e);
        }

        return build.toString();
    }

    /**
     * post请求，返回json
     */
    public static JSONObject httpPost(String requestURL, Map<String, String> postParams) {
        String str = sendPostRequest(requestURL, postParams);
        return JSONObject.parseObject(str);
    }

    /**
     * post请求，返回json
     */
    public static JSONObject httpPostJson(String requestURL, Map<String, String> postParams) {
        String str = sendPostJson(requestURL, postParams);
        // 将返回的String转化成json对象
        return JSONObject.parseObject(str);
    }

    /**
     * get请求，返回json
     */
    public static JSONObject httpGet(String requestURL) {
        String str = sendGetRequest(requestURL);
        return JSONObject.parseObject(str);
    }

    /**
     * Get请求，返回json
     */
    public static JSONObject httpGet(String requestURL, Map<String, String> postParams) {
        String str = sendGetRequest(requestURL, postParams);
        return JSONObject.parseObject(str);
    }

    public static void main(String[] args) {
        String szUrl = "http://192.168.1.31:8080/portal/entrance/http_index.jsp?userinfo=FeqONybLFwKn1pu135lk7DSYw8UpO66uc7Qc2giX2qtSFAo6F0bcLMpP%2F6wBJCCZhYnntUoUDaCPlDkjWZ0i4YqczEVILYHrvid3mMoxdcY%2FwJz0ErDAk%2BmSbkdooHEG&userip=192.168.1.146&userPublicIp=192.168.1.146&language=Chinese";
        String res = HttpClientHelper.sendGetRequest(szUrl);
        log.info(res);
    }


}
