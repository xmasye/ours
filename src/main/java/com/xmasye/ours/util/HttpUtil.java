package com.xmasye.ours.util;

import okhttp3.*;
import okhttp3.Request.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final OkHttpClient httpClient;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
    public static final String DEFAULT_CHARSET = "utf-8";
    private static final Map<String, String> DEFAULT_HEADERS = new HashMap(4);

    public HttpUtil() {
    }

    public static String get(String url) {
        return get(url, "utf-8", (Map)null, DEFAULT_HEADERS);
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, "utf-8", params, DEFAULT_HEADERS);
    }

    public static String get(String url, String charset, Map<String, String> params, Map<String, String> headers) {
        Assert.notNull(url, "url不能为空");

        try {
            String fullUrl = contactUrl(url, charset, params);
            Builder builder = new Builder();
            builder.get().url(fullUrl);
            if (headers != null && headers.size() > 0) {
                Iterator var6 = headers.entrySet().iterator();

                while(var6.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry)var6.next();
                    builder.addHeader((String)entry.getKey(), (String)entry.getValue());
                }
            }

            Request request = builder.build();
            Call call = httpClient.newCall(request);
            long startTime = System.currentTimeMillis();
            Response response = call.execute();
            if (!response.isSuccessful()) {
                logger.error("请求url出错 | url: {}, response.code: {}", fullUrl, response.code());
                throw new IllegalStateException(response.code() + " - 请求服务出错");
            } else {
                long costTime = System.currentTimeMillis() - startTime;
                logger.info("请求URL成功 | url: {}, charset: {}, costTime: {}", new Object[]{url, charset, costTime});
                return response.body().string();
            }
        } catch (IOException var13) {
            logger.error("读取url失败 | url, charset :{}", url, charset);
            throw new RuntimeException("读取url失败", var13);
        }
    }

    public static String contactUrl(String url, String charset, Map<String, String> params) throws IOException {
        if (params != null && !params.isEmpty()) {
            StringBuilder result = new StringBuilder(128);
            result.append(url);
            if (url.indexOf("?") < 0) {
                result.append("?");
            }

            Iterator var4 = params.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var4.next();
                result.append((String)entry.getKey()).append("=").append(URLEncoder.encode((String)entry.getValue(), charset)).append("&");
            }

            result.setLength(result.length() - 1);
            return result.toString();
        } else {
            return url;
        }
    }

    static {
        httpClient = (new okhttp3.OkHttpClient.Builder()).connectTimeout(8L, TimeUnit.SECONDS).readTimeout(15L, TimeUnit.SECONDS).writeTimeout(8L, TimeUnit.SECONDS).followRedirects(true).followSslRedirects(true).connectionPool(new ConnectionPool()).build();
        DEFAULT_HEADERS.put("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6");
    }

}
