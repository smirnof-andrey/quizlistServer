package com.asmirnov.quilzistServer.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthenticationUserFilter extends GenericFilterBean {

//    @Autowired
//    private UserService userService;
//
//    private User user;
//
//    @PostConstruct
//    public void init(){
//        // just hardcode for test
//        user = (User)userService.loadUserByUsername("Vasya");
//    }

    private final TokenAuthService tokenAuthService;

    public AuthenticationUserFilter(TokenAuthService tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthService.getAuthentication((HttpServletRequest) servletRequest).orElse(null)
        );
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
