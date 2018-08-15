package com.itmayiedu.service;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.common.base.BaseApiService;
import com.itmayiedu.common.base.BaseRedisService;
import com.itmayiedu.common.base.ResponseBase;
import com.itmayiedu.common.constants.Constants;
import com.itmayiedu.common.utils.MD5Util;
import com.itmayiedu.common.utils.TokenUtils;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.mapper.MemberMapper;
import com.itmayiedu.mq.RegisterMailboxProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Mr.PanYang on 2018/8/9.
 */
@Slf4j
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BaseRedisService baseRedisService;

    @Value("${messages.queue}")
    private String MESSAGESQUEUE;

    /**
     * @Author: My.PanYang
     * @Description: 根据id查找用户
     * @Date: 10:23 2018/8/10
     */
    @Override
    public ResponseBase findUserById(long id) {
        UserEntity user = memberMapper.findByID(id);
        if (StringUtils.isEmpty(user))
            return setResultError("未查找到用户信息!");
        return setResultSuccess(user);
    }

    /**
     * @Author: My.PanYang
     * @Description: 注册用户
     * @Date: 10:23 2018/8/10
     */
    @Override
    public ResponseBase registerUser(@RequestBody UserEntity user) {
        String password = user.getPassword();
        if (StringUtils.isEmpty(password))
            return setResultError("密码不能为空");
        //简单MD5加密
        String newPassword = MD5Util.MD5(password);
        user.setPassword(newPassword);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        int result = memberMapper.insertUser(user);
        if (result <= 0)
            return setResultError("注册失败");
        //  采用异步方式发送邮件
        String email = user.getEmail();
        String json = emailJson(email);
        log.info("会员服务推送消息到消息服务平台：" + json);
        sendMsg(json);
        return setResultSuccess("注册成功");
    }

    @Override
    public ResponseBase login(@RequestBody UserEntity user) {

        /*  1.验证数据
        *   2.数据库查找账号、密码是否正确
        *   3.如果正确，生成token
        *   4.存放到redis中，key : tokenId   value : userId
        *   5.返回token
        * */

        //验证数据
        String userName = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(userName))
            return setResultError("用户名不能为空！");
        if (StringUtils.isEmpty(password))
            return setResultError("密码不能为空！");
        //查找用户
        String newPassword = MD5Util.MD5(password);
        UserEntity userEntity = memberMapper.login(userName, newPassword);
        if (userEntity == null)
            return setResultError("账号或密码不正确");
        //生成token
        String memberToken = TokenUtils.getMemberToken();
        String userId = userEntity.getId().toString();
        //存入redis中
        baseRedisService.setString(memberToken, userId, Constants.TOKEN_MEMBER_TIME);
        log.info("用户token存放到redis中，key为{}，value为{}", memberToken, userId);
        //  存入数据库，用于限制只能一个用户登录（后续添加的功能）
        memberMapper.updateUserTokenById(memberToken, Long.parseLong(userId));
        log.info("存入数据库，用于限制只能一个用户登录（后续添加的功能）");
        //返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }

    @Override
    public ResponseBase findByToken(@RequestParam("token") String token) {
        /*
        *   1.验证数据
        *   2.从redis中查询用户信息返回给客户端
        *   3.使用userID到数据库查询用户信息返回给客户端
        * */
        if (StringUtils.isEmpty(token))
            return setResultError("token 不能为空");
        String redisUserId = baseRedisService.getString(token).toString();
        if (StringUtils.isEmpty(redisUserId))
            return setResultError("token 无效或者已过期");
        Long userId = Long.parseLong(redisUserId);
        UserEntity userEntity = memberMapper.findByID(userId);
        if (userEntity == null)
            return setResultError("未查找到用户信息");
        userEntity.setPassword(null);
        String newToken = userEntity.getToken();
        //  处理仅限一个用户登录的业务
        if (!token.equals(newToken)) {
            //  删除redis中旧的token
            baseRedisService.delKey(token);
            return setResultError("对不起，你的账号在其他设备登录，请重新登录");
        }
        return setResultSuccess(userEntity);
    }

    // region   发送邮件

    /**
     * @Author: My.PanYang
     * @Description: 组装发送邮件的 Json
     * @Date: 10:22 2018/8/10
     */
    private String emailJson(String email) {
        JSONObject rootJson = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.MSG_EMAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        rootJson.put("header", header);
        rootJson.put("content", content);
        return rootJson.toJSONString();
    }

    /**
     * @Author: My.PanYang
     * @Description: 发送邮件
     * @Date: 10:22 2018/8/10
     */
    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue, json);
    }
    // endregion

}
