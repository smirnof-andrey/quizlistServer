package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.dto.AuthResponseDTO;
import com.asmirnov.quilzistServer.model.Role;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.asmirnov.quilzistServer.security.TokenHandler;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@NoArgsConstructor
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    private AuthResponseDTO authResponseDTO;

    public AuthResponseDTO userAuthentication(String username, String password) {
        authResponseDTO = new AuthResponseDTO();
        try {
            User user = userRepo.findByUsernameAndPassword(username, password);
            if(user == null) {
                authResponseDTO.setResponseCode(1);
            }else{
                setUserToResponse(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            authResponseDTO.setResponseCode(9,e.getMessage());
        }
        log.debug("User authentication. {}", authResponseDTO.toString());
        return authResponseDTO;
    }

    public AuthResponseDTO createNewUser(User user) {
        authResponseDTO = new AuthResponseDTO();

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb == null){
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            setUserToResponse(userRepo.save(user));
        }else {
            authResponseDTO.setResponseCode(3);
        }
        log.debug("Create new user. {}", authResponseDTO.toString());
        return authResponseDTO;
    }

    public void setUserToResponse(User user){
        authResponseDTO.setUser(user);
        createNewToken();
    }

    public void createNewToken() {
        if(authResponseDTO.getUser() == null){
            authResponseDTO.setResponseCode(1);
        }else{
            TokenHandler tokenHandler = new TokenHandler();
            String newToken = tokenHandler
                    .generateAccessToken(authResponseDTO.getUser().getId(), LocalDateTime.now().plusMinutes(30));
            if (newToken == null || newToken.isEmpty()){
                authResponseDTO.setResponseCode(2);
            }else{
                authResponseDTO.setToken(newToken);
                authResponseDTO.setResponseCode(0);
            }
        }
    }
}
