package com.itmayiedu.common.base;

import com.itmayiedu.common.constants.Constants;

/**
 * Created by Mr.PanYang on 2018/8/8.
 */
public class BaseApiService {

    //  返回错误
    public ResponseBase setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    //  返回成功，需要传值data
    public ResponseBase setResultSuccess(Object data) {
        return setResult(Constants.HTTP_RES_CODE_200, null, data);
    }

    //  返回成功，需要传值msg
    public ResponseBase setResultSuccess(String msg) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }

    //  返回成功，没有data
    public ResponseBase setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_MSG, null);
    }

    //  通用封装
    public ResponseBase setResult(Integer code, String msg, Object data) {
        return new ResponseBase(code, msg, data);
    }

}
