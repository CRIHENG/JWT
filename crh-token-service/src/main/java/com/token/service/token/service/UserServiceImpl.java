package com.token.service.token.service;

import com.token.service.token.mapper.UserMapper;
import crh.token.api.entity.User;
import crh.token.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/token/user")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserName(@RequestParam("userName") String userName) {
        User user = userMapper.findByUserName(userName);
        return user;
    }

    @Override
    public User findUserByUserId(@RequestParam("userId") String userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    @Override
    public void save(User user) {
        userMapper.saveUser(user);
    }
}
