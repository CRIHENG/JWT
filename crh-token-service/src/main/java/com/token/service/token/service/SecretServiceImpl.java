package com.token.service.token.service;


import com.token.service.util.JWTConfig;
import crh.token.api.service.SecretService;
import crh.token.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTConfig jwtConfig;

    @Override
    public String secret(String userId) {

        String secret = null;
        if (userId != null) {
            secret =userService.findUserByUserId(userId).getPassword();
        }else {
            secret=jwtConfig.getSecret();
        }
        return secret;
    }
}
