package com.itmayiedu.controller;

import com.itmayiedu.common.base.ResponseBase;
import com.itmayiedu.common.constants.Constants;
import com.itmayiedu.common.utils.CookieUtil;
import com.itmayiedu.feign.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * Created by Mr.PanYang on 2018/8/14.
 */
@Controller
public class IndexController {

    private static final String INDEX = "index";
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        /*
        *   1.从cookie获取token
        *   2.如果存在token，调用会员服务接口， 使用token查询用户信息
        *
        * */
        String token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            ResponseBase responseBase = memberServiceFeign.findByToken(token);
            if (responseBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
                LinkedHashMap userData = (LinkedHashMap) responseBase.getData();
                String username = (String) userData.get("username");
                request.setAttribute("username", username);
            }
        }
        return INDEX;
    }

}
