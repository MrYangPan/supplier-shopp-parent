package com.itmayiedu.service;

import com.itmayiedu.common.base.BaseApiService;
import com.itmayiedu.common.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.PanYang on 2018/8/8.
 */
@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService {
    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("msg", "success");
        result.put("data", "id:" + id + ",name:" + name);
        return result;
    }

    @Override
    public ResponseBase testResponseBase() {
        return setResultSuccess("成功");
    }
}
