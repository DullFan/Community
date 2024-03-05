package com.ruoyi.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 邮件管理
 */
@Service
public class EmailUtils implements Serializable {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    public void sendEmailText(String title,String content,String receiver){
        SimpleMailMessage message = new SimpleMailMessage();
        //设置发送者邮箱
        message.setFrom(mailProperties.getUsername());
        //设置接收者邮件
        message.setTo(receiver);
        //设置邮箱主题
        message.setSubject(title);
        //设置邮箱内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }
}
