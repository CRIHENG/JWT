package crh.token.api.service;

import crh.token.api.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "token-service")
@RequestMapping("/token/user")
public interface UserService {
    //通过用户名获取用户信息
    @RequestMapping()
    public User findUserByUserName(String userName);

    //通过用户id获取用户信息
    @RequestMapping(value = "/findUserByUserId", method = RequestMethod.POST)
    public User findUserByUserId(String userId);

    //添加用户
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(User user);

}
