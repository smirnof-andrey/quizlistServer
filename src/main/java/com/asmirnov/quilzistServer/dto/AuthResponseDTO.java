package com.asmirnov.quilzistServer.dto;

import com.asmirnov.quilzistServer.AuthViews;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.security.TokenHandler;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"token"})
public class AuthResponseDTO {

    @JsonView(AuthViews.AuthInfo.class)
    private User user;

    @JsonView(AuthViews.AuthInfo.class)
    private String token;

    @JsonView(AuthViews.AuthInfo.class)
    private int errorCode;

    @JsonView(AuthViews.AuthInfo.class)
    private String message;

    public void setErrorCode(int errorCode) {
        setErrorCode(errorCode, "");
    }

    public void setErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        if(message.isEmpty()){
            switch(errorCode) {
                case 0:
                    message = "success";
                    break;
                case 1:
                    message = "incorrect user or password";
                    break;
                case 2:
                    message = "token generate error";
                    break;
                case 3:
                    message = "This user exists";
                    break;
            }
            this.message = message;
        }
    }

}
