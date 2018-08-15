package com.itmayiedu.service;

import com.itmayiedu.common.base.ResponseBase;
import com.itmayiedu.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Mr.PanYang on 2018/8/9.
 */
@RequestMapping("memberApi")
public interface MemberService {

    // 使用userId查找用户
    @RequestMapping("findUserById")
    ResponseBase findUserById(long id);

    @RequestMapping("registerUser")
    ResponseBase registerUser(@RequestBody UserEntity user);

    // 用户登录
    @RequestMapping("login")
    ResponseBase login(@RequestBody UserEntity user);

    //  使用token登录
    @RequestMapping("findByToken")
    ResponseBase findByToken(@RequestParam("token") String token);

}
