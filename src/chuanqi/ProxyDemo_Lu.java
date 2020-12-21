package chuanqi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
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
public class ProxyDemo_Lu {
    static boolean flagOfProxy = true;
    static boolean flagOfProxy_lu = true;
//    static boolean flagOfProxy = false;

    public static void main(String[] args) {
        ProxyDemo_Lu proxyDemo = new ProxyDemo_Lu();
//        proxyDemo.fetch("http://www.baidu.com/s?ie=utf-8&wd=ip", "baidu_proxy.html");
        proxyDemo.fetch("https://www.bbc.com/news", "bbc_proxy.html");
//        proxyDemo.fetch("https://www.youtube.com/", "youtube_"+flagOfProxy+".html");
//        proxyDemo.fetch("https://www.bbc.com/news/election-us-2020-54472975", "bbc_proxy.html");
//        proxyDemo.fetch("https://www.bbc.com/", "bbc_proxy.html");
//        proxyDemo.fetch("https://edition.cnn.com/", "cnn_proxy.html");
    }

    private void fetch(String url, String path) {
        HttpHost target = new HttpHost("www.bbc.com", 443, "https");
        HttpGet request = new HttpGet("/news");

        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");

        CloseableHttpClient httpClient;
        System.out.println("flagOfProxy="+flagOfProxy);
        System.out.println("flagOfProxy_lu="+flagOfProxy_lu);
        if(flagOfProxy) {
            if(flagOfProxy_lu) {
                HttpHost proxy = new HttpHost("127.0.0.1", 8087);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                request.setConfig(config);
                httpClient = HttpClients.custom().build();
            } else {
                HttpHost proxy = new HttpHost("23.234.250.223", 3128);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                request.setConfig(config);
//                CredentialsProvider provider = new BasicCredentialsProvider();
//                provider.setCredentials(
//                        new AuthScope("23.234.250.223", 3128),
//                        new UsernamePasswordCredentials("chuanqi", "legend123")
//                );
//                httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
                httpClient = HttpClients.custom().build();
            }
        } else {
            httpClient = HttpClients.custom().build();
        }
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(target, request);
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
