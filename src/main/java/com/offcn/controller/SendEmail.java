package com.offcn.controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendEmail {

    public static String sendPic(String name,String userid,String groupid,String userinfo,String base64Pic) throws MessagingException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-email.xml");
        JavaMailSenderImpl sender = context.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = sender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        messageHelper.setFrom("maotouying0519@sina.com");
        messageHelper.setTo("maotouying0519@sina.com");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        messageHelper.setSubject("登录信息:"+userid+"于"+today+"人脸登录成功!");

        messageHelper.setText("<h1>用户"+userid+"于:"+today+"完成登录</h1><p>用户信息:"+userinfo+",所属组:"+groupid+"</p><img src='cid:hgf'></img>",true);
        messageHelper.addInline("hgf",new File("/Users/haogaofeng/Downloads/JFreeChart/"+name+""));

        sender.send(mimeMessage);
        return "ok";
    }
}
