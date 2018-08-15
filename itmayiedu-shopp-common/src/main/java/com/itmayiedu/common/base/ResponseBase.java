package com.itmayiedu.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mr.PanYang on 2018/8/8.
 */
@Getter
@Setter
public class ResponseBase {
    private Integer code;
    private String msg;
    private Object data;

    public ResponseBase() {
    }

    public ResponseBase(Integer code, String msg, Object object) {
        this.code = code;
        this.msg = msg;
        this.data = object;
    }
}
