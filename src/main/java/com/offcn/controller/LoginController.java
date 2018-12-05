package com.offcn.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.offcn.util.FaceSerach;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Controller
public class LoginController {


    @RequestMapping("faceLogin")
    @ResponseBody
    public String faceLogin(String imageData) throws IOException, MessagingException {
        String face = FaceSerach.findFace(imageData);
        //把字符串转换成json对象
        JSONObject parseObject = JSONObject.parseObject(face);
        Integer code = parseObject.getInteger("error_code");
        if(code == 0){
            //获取result
            JSONObject result = parseObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("user_list");
            JSONObject obj =(JSONObject)list.get(0);
            Double score = obj.getDouble("score");
            String groupId = obj.getString("group_id");
            String userId = obj.getString("user_id");
            String userInfo = obj.getString("user_info");
            if(score>=70){
                sendEmail(imageData,userId,groupId,userInfo);
            }
        }
        return face;
    }




        /*
         * {
         *   "result":{
         *               "face_token":"63e13c253d54463d3fe7733dcda67ada",
         *               "user_list":
         *                           [{"score":85.039245605469,"group_id":"0723",
         *                           "user_id":"haogaofeng","user_info":""}]
         *            },
         *   "log_id":305486838431805521,
         *   "error_msg":"SUCCESS",
         *   "cached":0,
         *   "error_code":0,
         *   "timestamp":1543843180
         * }
         * */



    public static void sendEmail(String imageData,String userid,String groupid,String userinfo) throws IOException, MessagingException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(imageData);
        String name = UUID.randomUUID()+".jpg";
        FileUtils.writeByteArrayToFile(new File("/Users/haogaofeng/Downloads/JFreeChart/"+name+""),bytes,true);
        SendEmail.sendPic(name,userid,groupid,userinfo,imageData);

    }

    public void end(){
        System.out.println("end");
    }
}
