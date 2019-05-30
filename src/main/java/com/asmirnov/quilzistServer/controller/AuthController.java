package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.model.AuthViews;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.model.Views;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.asmirnov.quilzistServer.model.AuthResponse;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/auth")
   // @JsonView(AuthViews.AuthInfo.class)
    public AuthResponse getToken(@RequestParam String username, @RequestParam String password ) {

        User user=null;

        try {
            user = userRepo.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AuthResponse authResponse = new AuthResponse(user);

        return authResponse;
    }
}
