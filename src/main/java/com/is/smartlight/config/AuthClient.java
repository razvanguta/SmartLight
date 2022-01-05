package com.is.smartlight.config;

import com.is.smartlight.dtos.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "auth", configuration = AuthClientConfig.class, url = "${my-keycloak.url}")
public interface AuthClient {

    @RequestMapping(method = RequestMethod.POST, value = "${my-keycloak.login-url}")
    TokenDto login(Map<String, ?> loginForm);

    @RequestMapping(method = RequestMethod.POST, value = "${my-keycloak.login-url}")
    TokenDto refresh(Map<String, ?> refreshForm);

}
