package com.asmirnov.quilzistServer.dto;

import com.asmirnov.quilzistServer.AuthViews;
import com.asmirnov.quilzistServer.model.User;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private int responseCode;

    @JsonView(AuthViews.AuthInfo.class)
    private String message;

    public void setResponseCode(int responseCode) {
        setResponseCode(responseCode, "");
    }

    public void setResponseCode(int errorCode, String message) {
        this.responseCode = errorCode;
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
                    message = "This user is exists";
                    break;
            }
            this.message = message;
        }
    }

}
