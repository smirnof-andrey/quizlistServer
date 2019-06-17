package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.dto.AuthResponseDTO;
import com.asmirnov.quilzistServer.model.Role;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.asmirnov.quilzistServer.security.TokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    private AuthResponseDTO authResponseDTO;

    public AuthService() {
        authResponseDTO = new AuthResponseDTO();
    }

    public AuthResponseDTO userAuthentication(String username, String password) {
        try {
            User user = userRepo.findByUsernameAndPassword(username, password);
            if(user == null) {
                authResponseDTO.setErrorCode(1);
            }else{
                setUserToResponse(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            authResponseDTO.setErrorCode(9,e.getMessage());
        }
        log.debug("User authentication. {}", authResponseDTO.toString());
        return authResponseDTO;
    }

    public AuthResponseDTO createNewUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb == null){
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            setUserToResponse(userRepo.save(user));
        }else {
            authResponseDTO.setErrorCode(3);
        }
        log.debug("Create new user. {}", authResponseDTO.toString());
        return authResponseDTO;
    }

    public void setUserToResponse(User user){
        authResponseDTO.setUser(userRepo.save(user));
        createNewToken();
    }

    public void createNewToken() {
        if(authResponseDTO.getUser() == null){
            authResponseDTO.setErrorCode(1);
        }else{
            TokenHandler tokenHandler = new TokenHandler();
            String newToken = tokenHandler
                    .generateAccessToken(authResponseDTO.getUser().getId(), LocalDateTime.now().plusMinutes(30));
            if (newToken == null || newToken.isEmpty()){
                authResponseDTO.setErrorCode(2);
            }else{
                authResponseDTO.setToken(newToken);
                authResponseDTO.setErrorCode(0);
            }
        }
    }
}
