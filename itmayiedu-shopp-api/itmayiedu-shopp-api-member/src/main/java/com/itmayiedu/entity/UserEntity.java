package com.itmayiedu.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Mr.PanYang on 2018/8/9.
 */
@Getter
@Setter
public class UserEntity {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Date created;
    private Date updated;
    private String token;
}
