package com.offcn.util;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FaceSerach {

    public static String findFace(String base64Pic) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String json = "";
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        String access_token = "24.3991f33044746ae67c74983094a12e96.2592000.1546327918.282335-15016790";

        HttpPost post = new HttpPost(url+"?access_token="+access_token);
        post.setHeader("Content-Type","application/json");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("image",base64Pic));
        list.add(new BasicNameValuePair("image_type","BASE64"));
        list.add(new BasicNameValuePair("group_id_list","0723"));

        post.setEntity(new UrlEncodedFormEntity(list));
        CloseableHttpResponse response = client.execute(post);
        int code = response.getStatusLine().getStatusCode();
        if(code == 200){
            HttpEntity entity = response.getEntity();
            json = EntityUtils.toString(entity, "utf-8");
            System.out.println(json);
        }
        return json;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/haogaofeng/Downloads/JFreeChart/baiduAi/hgf1.jpg");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        Base64.Encoder encoder = Base64.getEncoder();
        String images = encoder.encodeToString(bytes);
        findFace(images);
    }
}
