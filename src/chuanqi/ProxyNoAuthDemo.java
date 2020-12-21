package chuanqi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Chuanqi Shi
 * @date 2020/Oct/09
 */
public class ProxyNoAuthDemo {
    public static void main(String[] args) {
        HttpGet request = new HttpGet("http://www.baidu.com/s?ie=utf-8&wd=ip");
        HttpHost proxy = new HttpHost("localhost", 8081);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        request.setConfig(config);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = response.getEntity();
        try {
            httpEntity.writeTo(new FileOutputStream("baidu_proxy_no_auth.html"));
            httpClient.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
