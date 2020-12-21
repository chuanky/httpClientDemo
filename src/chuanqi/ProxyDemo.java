package chuanqi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Chuanqi Shi
 * @date 2020/Oct/08
 */
public class ProxyDemo {
    public static void main(String[] args) {
        ProxyDemo proxyDemo = new ProxyDemo();
        proxyDemo.fetch("https://www.baidu.com/s?ie=utf-8&wd=ip", "baidu_proxy.html");
//        proxyDemo.fetch("https://www.bbc.com/news", "bbc_proxy.html");
    }

    private void fetch(String url, String path) {
        HttpGet request = new HttpGet(url);
        HttpHost proxy = new HttpHost("23.234.250.223", 3128);
        // request config
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        request.setConfig(config);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        // httpClient config
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(
                new AuthScope("23.234.250.223", 3128),
                new UsernamePasswordCredentials("chuanqi", "legend123")
        );
        CloseableHttpClient httpClient = HttpClients.custom()
                .setProxy(proxy)
                .setDefaultCredentialsProvider(provider)
                .build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = response.getEntity();
        try {
            httpEntity.writeTo(new FileOutputStream(path));
            httpClient.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
