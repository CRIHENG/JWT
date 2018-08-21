package com.token.service.token.mapper;

import crh.token.api.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @SelectProvider(type = UserProvider.class,method = "findByUserName")
    User findByUserName(String userName);

    @SelectProvider(type = UserProvider.class,method = "findByUserId")
    User findByUserId(String userId);
    @InsertProvider(type = UserProvider.class,method = "saveUser")
    void  saveUser(User user);
}
