package com.asmirnov.quilzistServer.controller.rest;

import com.asmirnov.quilzistServer.dto.AuthResponseDTO;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.asmirnov.quilzistServer.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authService;

    @GetMapping("/auth")
    public AuthResponseDTO getToken(@RequestParam String username, @RequestParam String password ) {
        return authService.userAuthentication(username, password);
    }

    @PostMapping("/newUser")
    public AuthResponseDTO createNewUser(@RequestBody User user) {
        return authService.createNewUser(user);
    }
}
