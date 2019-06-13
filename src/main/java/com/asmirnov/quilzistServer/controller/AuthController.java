package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.dto.AuthResponse;
import com.asmirnov.quilzistServer.model.*;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
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
    public AuthResponse createModule(@RequestBody User user) {

        AuthResponse authResponse;

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb == null){
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            authResponse = new AuthResponse(userRepo.save(user));
        }else {
            authResponse = new AuthResponse(3,"This user exists");
        }

        return authResponse;
    }
}
