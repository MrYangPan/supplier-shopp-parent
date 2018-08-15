package com.itmayiedu.mapper;

import com.itmayiedu.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.PanYang on 2018/8/9.
 */
@Component
public interface MemberMapper {

    //    @Select("select id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
    UserEntity findByID(@Param("userId") Long userId);

    //    @Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
    Integer insertUser(UserEntity userEntity);

    UserEntity login(@Param("userName") String userName, @Param("password") String password);

    //  根据用户id更新token
    void updateUserTokenById(@Param("token") String token, @Param("userId") Long userId);
}
