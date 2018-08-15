package com.itmayiedu.controller;

import com.itmayiedu.common.base.ResponseBase;
import com.itmayiedu.common.constants.Constants;
import com.itmayiedu.common.utils.CookieUtil;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.feign.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * Created by Mr.PanYang on 2018/8/15.
 */
@Controller
public class LoginController {

    private static final String REDIRECT_INDEX = "redirect:/";//重定向
    private static final String LOGIN = "login";
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginGet() {
        return LOGIN;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        /*
        *   1.验证参数
        *   2.调用登录接口，获取token信息
        *   3.将token信息存放到cookie
        * */
        ResponseBase login = memberServiceFeign.login(userEntity);
        if (!login.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", "账号或者密码错误");
            return LOGIN;
        }
        LinkedHashMap loginData = (LinkedHashMap) login.getData();
        String memberToken = (String) loginData.get("memberToken");
        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "会话已失效");
            return LOGIN;
        }
        //存入cookie
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_TOKEN_MEMBER_TIME);
        return REDIRECT_INDEX;
    }


}
