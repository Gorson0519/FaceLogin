package com.offcn.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class getAccessToken {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String url = "https://aip.baidubce.com/oauth/2.0/token";
        String grant_type = "client_credentials";
        String client_id = "YFTwSKSUDZVLR4Y9EXMszghy";
        String client_secret = "Bd1por73DqvRrkFIR7ZBAMbAH5XeEjO8";

        HttpPost post = new HttpPost(url+"?grant_type="+grant_type+"&client_id="+client_id+"&client_secret="+client_secret);

        CloseableHttpResponse response = client.execute(post);
        int code = response.getStatusLine().getStatusCode();
        if(code == 200){
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity, "utf-8");
            System.out.println(json);
        }
        client.close();
    }
}
