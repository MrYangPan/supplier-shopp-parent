package com.itmayiedu.service;

import com.itmayiedu.common.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Mr.PanYang on 2018/8/9.
 */
@RequestMapping("member")
public interface TestApiService {

    @RequestMapping("test")
    Map<String, Object> test(Integer id, String name);

    @RequestMapping("testResponseBase")
    ResponseBase testResponseBase();
}
