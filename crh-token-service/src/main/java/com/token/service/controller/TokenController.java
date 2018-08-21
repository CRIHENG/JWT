package com.token.service.controller;

import com.common.service.util.ResponseBean;
import com.token.service.common.RedisUtil;
import com.token.service.util.JWTConfig;
import com.token.service.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class TokenController {
    Logger log = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JWTConfig jwtConfig;

    @GetMapping("/refreshToken")
    @ResponseBody
    public ResponseBean refreshToken(HttpServletRequest request) {
        log.info("token-service-------refreshToken");
        try {
            String refreshToken = request.getHeader("token");
            String accessToken = request.getHeader("accessToken");

            RedisUtil.set(redisTemplate, accessToken, refreshToken, new Long(jwtConfig.getExp()));
            RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getExp()));

            String userId = JWTUtil.getUserId(refreshToken);
            String secret = jwtConfig.getSecret();

            refreshToken = JWTUtil.sign(userId, secret, new Long(jwtConfig.getRefreshToken()));
            accessToken = JWTUtil.sign(userId, secret, new Long(jwtConfig.getAccessToken()));

            RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getAccessToken()));
            RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getRefreshToken()));

            return new ResponseBean(200, "accessToken: " + accessToken + ", refreshToken:" + refreshToken, "success");
        } catch (Exception e) {
            log.error(e.toString());
        }
        return new ResponseBean(200, "error", null);
    }

    @GetMapping("/logout")
    public ResponseBean logout(@RequestParam("refreshToken") String refreshToken, @RequestParam("accessToken") String accessToken) {


        RedisUtil.set(redisTemplate, accessToken, accessToken, new Long(jwtConfig.getExp()));
        RedisUtil.set(redisTemplate, refreshToken, accessToken, new Long(jwtConfig.getExp()));
        return new ResponseBean(200, null, "success");

    }
}
