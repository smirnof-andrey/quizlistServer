package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.model.*;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("/newUser")
    public Map<String, Object> createModule(@RequestBody User user) {

        Map<String, Object> model = new HashMap<>();
        model.put("errorCode",0);

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            // User exists
            model.put("errorCode",1);
            return model;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        model.put("newUser",user);

        return model;
    }
}
