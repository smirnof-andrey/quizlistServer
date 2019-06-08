package com.asmirnov.quilzistServer.model;

import com.asmirnov.quilzistServer.service.TokenHandler;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class AuthResponse {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonView(AuthViews.AuthInfo.class)
    private User user;

    @JsonView(AuthViews.AuthInfo.class)
    private String token;

    @JsonView(AuthViews.AuthInfo.class)
    private int errorCode;    // 0 - success, 1 - user not found, 2 - token generate is fall, 3 - user exists (registration)

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

    public AuthResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
