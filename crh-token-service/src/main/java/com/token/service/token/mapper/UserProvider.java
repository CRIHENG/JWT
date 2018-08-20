package com.token.service.token.mapper;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {

    public String findByUserName(String userName) {
        SQL sql = new SQL().SELECT("*").FROM("user").WHERE("username=#{username}");
        return sql.toString();
    }
}
