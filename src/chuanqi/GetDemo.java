package chuanqi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Chuanqi Shi
 * @date 2020/Oct/09
 */
public class GetDemo {
    public static void main(String[] args) {
//        HttpGet request = new HttpGet("http://www.baidu.com/s?ie=utf-8&wd=ip");
        HttpGet request = new HttpGet("https://www.bbc.com/news");
//        HttpGet request = new HttpGet("https://edition.cnn.com/");
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        request.setConfig(config);
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            httpEntity.writeTo(new FileOutputStream("bbc.html"));
            EntityUtils.consume(httpEntity);
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
