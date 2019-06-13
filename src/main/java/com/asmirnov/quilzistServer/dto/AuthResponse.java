package com.asmirnov.quilzistServer.dto;

import com.asmirnov.quilzistServer.AuthViews;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.security.TokenHandler;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

public class AuthResponse {

    @JsonView(AuthViews.AuthInfo.class)
    private User user;

    @JsonView(AuthViews.AuthInfo.class)
    private String token;

    // errorCode:
    // 0 - success,
    // 1 - user not found,
    // 2 - token generate is fall,
    // 3 - user exists (registration)

    @JsonView(AuthViews.AuthInfo.class)
    private int errorCode;

    @JsonView(AuthViews.AuthInfo.class)
    private String message;

    public AuthResponse(User user) {

        this.user = user;

        if(this.user == null){
            this.errorCode = 1;
            this.message = "incorrect user/password";
        }else{
            TokenHandler tokenHandler = new TokenHandler();
            this.token = tokenHandler.generateAccessToken(user.getId(), LocalDateTime.now().plusMinutes(30));
            if (this.token == null || this.token.isEmpty()){
                this.errorCode = 2;
                this.message = "token generate error";
            }else{
                this.errorCode = 0;
                this.message = this.user.getUsername();
            }
        }
    }

    public AuthResponse(int errorCode) {
        this(errorCode, "");
    }

    public AuthResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        if(message.isEmpty()){
            switch(errorCode) {
                case 0:
                    message = "success";
                    break;
                case 1:
                    message = "user not found";
                    break;
                case 2:
                    message = "token generate is fall";
                    break;
                case 3:
                    message = "This user exists";
                    break;
            }
            this.message = message;
        }
    }
}
