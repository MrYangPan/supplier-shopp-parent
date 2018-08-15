package com.itmayiedu.controller;

import com.itmayiedu.common.base.ResponseBase;
import com.itmayiedu.common.constants.Constants;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.feign.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Mr.PanYang on 2018/8/14.
 */
@Controller
public class RegisterController {

    private static final String REGISTER = "register";
    private static final String LOGIN = "login";
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    // 跳转注册页面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet() {
        return REGISTER;
    }

    /**
     * @Author: My.PanYang
     * @Description: 注册
     * @Date: 16:52 2018/8/14
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerPost(UserEntity userEntity, HttpServletRequest request) {
        /*
        *   1.验证参数
        *   2.调用会员注册接口
        *   3.跳转页面
        * */
        ResponseBase regUser = memberServiceFeign.registerUser(userEntity);
        //  如果失败，跳转到失败页面
        if (!regUser.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", "注册失败");
            return REGISTER;
        }
        //  如果成功,跳转到成功页面
        return LOGIN;
    }


}
