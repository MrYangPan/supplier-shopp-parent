package com.itmayiedu.common.utils;

import com.itmayiedu.common.constants.Constants;

import java.util.UUID;

public class TokenUtils {

    public static String getMemberToken() {
        return Constants.TOKEN_MEMBER + "-" + UUID.randomUUID();
    }

}
