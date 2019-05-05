package com.asmirnov.quilzistServer.model;

import com.asmirnov.quilzistServer.service.TokenHandler;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class AuthResponse {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private User user;

    @JsonView(AuthViews.AuthInfo.class)
    private String token;

    @JsonView(AuthViews.AuthInfo.class)
    private String message;

    @JsonView(AuthViews.AuthInfo.class)
    private int errorCode;    // 0 - user is found, 1 - user not found, 2 - token generate is fall

    public AuthResponse() {
    }

    public AuthResponse(User user) {

        this.user = user;

        if(this.user == null){
            this.errorCode = 1;
            this.message = "user is not found";
        }else{
            TokenHandler tokenHandler = new TokenHandler();
            this.token = tokenHandler.generateAccessToken(user.getId(), LocalDateTime.now().plusDays(1));
            if (this.token == null || this.token.isEmpty()){
                this.errorCode = 2;
                this.message = "token generate error";
            }else{
                this.errorCode = 0;
                this.message = this.user.getUsername();
            }
        }

    }
}
