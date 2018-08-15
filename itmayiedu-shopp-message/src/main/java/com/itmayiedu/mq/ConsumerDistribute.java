package com.itmayiedu.mq;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.adapter.MessageAdapter;
import com.itmayiedu.common.constants.Constants;
import com.itmayiedu.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Mr.PanYang on 2018/8/9.
 * <p>
 * 消息管理接口
 */
@Slf4j
@Component
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageAdapter messageAdapter;

    /**
     * @Author: My.PanYang
     * @Description: 监听消息队列
     * @Date: 17:55 2018/8/9
     */
    @JmsListener(destination = "messages_queue")
    public void distribute(String json) {
        log.info("####ConsumerDistribute###distribute() 消息服务平台接受 json参数:" + json);
        if (StringUtils.isEmpty(json)) {
            //  没有消息，直接返回
            return;
        }
        JSONObject rootJson = new JSONObject().parseObject(json);
        JSONObject header = rootJson.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        if (StringUtils.isEmpty(interfaceType))
            return;
        // 判断接口类型是否为发邮件
        if (interfaceType.equals(Constants.MSG_EMAIL))
            messageAdapter = emailService;
        JSONObject contentJson = rootJson.getJSONObject("content");
        messageAdapter.sendMsg(contentJson);
    }


}
