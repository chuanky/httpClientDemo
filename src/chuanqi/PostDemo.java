package chuanqi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Chuanqi Shi
 * @date 2020/Dec/21
 */
public class PostDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost("http://search.people.cn/api-search/elasticSearch/search");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        String payload = "{\"key\":\"大选\",\"page\":1,\"limit\":20,\"hasTitle\":true,\"hasContent\":true,\"isFuzzy\":true,\"type\":7,\"domain\":\"world.people.com.cn\",\"sortType\":2,\"startTime\":0,\"endTime\":0}";
        StringEntity payloadEntity = new StringEntity(payload, StandardCharsets.UTF_8);
        httpPost.setEntity(payloadEntity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            httpEntity.writeTo(new FileOutputStream("people_search.json"));
            EntityUtils.consume(httpEntity);
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
