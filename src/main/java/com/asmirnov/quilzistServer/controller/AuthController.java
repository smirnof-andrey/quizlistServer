package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.security.TokenAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthController {

//    @Autowired
//    private TokenAuthService tokenAuthService;

    @GetMapping("/auth")
    public List<String> createModule(@RequestParam String username, @RequestParam String password ) {
        List<String> respounse = new ArrayList<>();
        respounse.add("12345");
        return respounse;
    }

}
