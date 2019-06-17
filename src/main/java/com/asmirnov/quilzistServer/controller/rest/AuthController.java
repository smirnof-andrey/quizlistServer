package com.asmirnov.quilzistServer.controller.rest;

import com.asmirnov.quilzistServer.dto.AuthResponseDTO;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/auth")
    public AuthResponseDTO userAuthentication(@RequestParam String username, @RequestParam String password ) {
        return authService.userAuthentication(username, password);
    }

    @PostMapping("/newUser")
    public AuthResponseDTO createNewUser(@RequestBody User user) {
        return authService.createNewUser(user);
    }
}
