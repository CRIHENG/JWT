package crh.token.api.service;

import org.springframework.stereotype.Service;

@Service
public interface SecretService {
    //获取secret
   public String secret(String userId);
}
