package com.janwarlen.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author janwarlen
 * @date 2018年12月20日11:29:19
 * @Description: http请求工具
 */
public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);


    public static String getAppLog(String url, String... params) {
        String body = "";
        CloseableHttpResponse response = null;
        //创建httpclient对象
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(encodeUrl(url, params));
            //装填参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            //设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpPost.setConfig(requestConfig);
            //执行请求操作，并拿到结果（同步阻塞）
            response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = entityToString(entity);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
            LOGGER.debug("HttpClientUtil:请求地址：" + url + " \n 请求参数：" + nvps.toString() + " \n 请求结果：" + body);
            return body;
        } catch (Exception e) {
            LOGGER.error("HttpClientUtil error:", e);
            return body;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }

    /**
     * get调用url
     *
     * @param url 目标url
     * @return 返回接口值，类型转换为JSONObject
     */
    public static String doGet(String url, String... params) {
        String body = "";
        CloseableHttpResponse response = null;
        //创建httpclient对象
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            HttpGet httpGet = new HttpGet(encodeUrl(url, params));
            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpGet.setConfig(requestConfig);
            //执行请求操作，并拿到结果（同步阻塞）
            response = client.execute(httpGet);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = entityToString(entity);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
            return body;
        } catch (Exception e) {
            LOGGER.error("HttpClientUtil error:", e);
            return body;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }

    private static String entityToString (HttpEntity entity) throws IOException {
        try (final InputStream inputStream = entity.getContent();) {
            final ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            if (charset == null) {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            final StringBuilder b = new StringBuilder();
            final char[] tmp = new char[1024];
            final Reader reader = new InputStreamReader(inputStream, charset);
            try {
                int l;
                while ((l = reader.read(tmp)) != -1) {
                    b.append(tmp, 0, l);
                }
            } catch (final ConnectionClosedException ignore) {

            }
            return b.toString();
        }
    }

    private static String encodeUrl(String url, String... params) {
        if (params.length == 0 || params.length % 2 != 0) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                urlBuilder.append("&");
            }
            try {
                urlBuilder.append(params[i]).append("=").append(URLEncoder.encode(params[i + 1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("HttpClientUtil info:"+url);
                LOGGER.error("HttpClientUtil params:", StringUtils.join(params, "&"));
                LOGGER.error("HttpClientUtil error:", e);
            }
        }
        return urlBuilder.toString();
    }
}
