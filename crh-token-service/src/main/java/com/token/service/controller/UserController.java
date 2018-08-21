package com.token.service.controller;

import com.auth0.jwt.JWT;
import com.common.service.util.ResponseBean;
import com.token.service.common.RedisUtil;
import com.token.service.util.JWTConfig;
import com.token.service.util.JWTUtil;
import crh.token.api.entity.User;
import crh.token.api.service.SecretService;
import crh.token.api.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private SecretService secretService;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    @ResponseBody
    public ResponseBean login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        password = new Md5Hash(password, "1028", 31).toBase64();
        ResponseBean responseBean = null;
        User user = userService.findUserByUserName(userName);
        if (user == null) {
            responseBean = new ResponseBean(200, null, "用户不存在");
        } else if (password.equals(user.getPassword())) {
            String userId = user.getId();
            String secret = secretService.secret(userId);
            String accessToken = JWTUtil.sign(userId, secret, new Long(jwtConfig.getAccessToken()));
            String refreshToken = JWTUtil.sign(userId, secret, new Long(jwtConfig.getRefreshToken()));

            RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getAccessToken()));
            RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getRefreshToken()));

            responseBean = new ResponseBean(200, "accessToken: " + accessToken + ", refreshToken:" + refreshToken, "success");
        } else {
            responseBean = new ResponseBean(200, null, "密码不正确");
        }
        return responseBean;
    }
    /**/


    @PostMapping("/addUser")
    @ResponseBody
    public ResponseBean addUser(@RequestBody User user) {

        ResponseBean responseBean = null;

        try {
            String password = new Md5Hash(user.getPassword(), "1028", 31).toBase64();

            user.setPassword(password);
            userService.save(user);
            responseBean = new ResponseBean(200, null, "success");
        } catch (Exception e) {
            logger.error("添加用户异常" + e.getMessage());
        }
        return responseBean;

    }
}
