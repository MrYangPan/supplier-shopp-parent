package com.itmayiedu.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Mr.PanYang on 2018/8/9.
 * <p>
 * 统一发送消息接口
 */
public interface MessageAdapter {

    void sendMsg(JSONObject body);
}
