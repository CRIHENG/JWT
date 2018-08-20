package crh.token.api.service;

import com.common.service.util.ResponseBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("token-service")
@RequestMapping("token/api")
public interface TokenService {
    @RequestMapping(value = "checkToken",method = RequestMethod.POST)
    public ResponseBean checkToken(@RequestParam("token") String token);
}
