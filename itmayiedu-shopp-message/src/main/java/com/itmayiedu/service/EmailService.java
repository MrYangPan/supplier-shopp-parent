package com.itmayiedu.service;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Mr.PanYang on 2018/8/10.
 * <p>
 * 处理第三方发送邮件
 */
@Service
@Slf4j
public class EmailService implements MessageAdapter {

    @Value("${msg.subject}")
    private String subject;

    @Value("${msg.text}")
    private String text;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMsg(JSONObject body) {
        String email = body.getString("email");
        if (StringUtils.isEmpty(email))
            return;
        log.info("开始发送邮件到：" + email + "邮箱");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //  来自账号，自己发自己
        simpleMailMessage.setFrom(email);
        //  发送账号
        simpleMailMessage.setTo(email);
        //  设置标题
        simpleMailMessage.setSubject(subject);
        //  设置内容
        simpleMailMessage.setText(text);
        //  发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("发送邮件完 ！(～￣▽￣)～ ");
    }
}
