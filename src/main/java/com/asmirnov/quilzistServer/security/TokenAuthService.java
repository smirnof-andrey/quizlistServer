package com.asmirnov.quilzistServer.security;

import com.asmirnov.quilzistServer.service.TokenHandler;
import com.asmirnov.quilzistServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class TokenAuthService {
    private static final String AUTH_HEADER_NAME = "X-Auth-Token";

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private UserService userService;

    public Optional<Authentication> getAuthentication(HttpServletRequest servletRequest) {

        return Optional
                .ofNullable(servletRequest.getHeader(AUTH_HEADER_NAME))
                .flatMap(tokenHandler::extractUserId)
                .flatMap(userService::findById)
                .map(UserAuthentication::new);
    }
}

