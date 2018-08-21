package com.token.service.token.mapper;

import crh.token.api.entity.User;
import org.apache.ibatis.jdbc.SQL;


public class UserProvider {


    private SQL getUser() {
        SQL sql = new SQL().SELECT("*").FROM("user");
        return sql;
    }


    public String findByUserName(String userName) {
        SQL sql = getUser().WHERE("username=#{username}");
        return sql.toString();
    }

    public String findByUserId(String userId) {
        SQL sql = getUser().WHERE("id=#{userId}");
        return sql.toString();
    }

    public String saveUser(User user) {
        SQL sql = new SQL().INSERT_INTO("user").
                VALUES("username", user.getUserName()).
                VALUES("password", user.getPassword()).
                VALUES("creationtime", user.getCreationtime()).
                VALUES("status", user.getStatus());
        return sql.toString();
    }
}
