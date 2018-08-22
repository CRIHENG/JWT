package com.token.service.token.service;

import com.common.service.util.ResponseBean;
import com.token.service.common.RedisUtil;
import com.token.service.util.JWTUtil;
import crh.token.api.service.SecretService;
import crh.token.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/token/api")
public class TokenServiceImpl implements TokenService {
    @Autowired

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SecretService secretService;

    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public ResponseBean checkToken(String token) {
       String userId=JWTUtil.getUserId(token);
       String secret=secretService.secret(userId);
       String flag=JWTUtil.verify(token,userId,secret);
       if (RedisUtil.get(redisTemplate,token)==null){
           return new ResponseBean(200,null,"RedisTokenExpired");
       }else {
           return new ResponseBean(200,null,flag);
       }
    }
}
