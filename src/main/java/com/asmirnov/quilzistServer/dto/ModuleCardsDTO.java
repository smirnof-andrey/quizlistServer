package com.asmirnov.quilzistServer.dto;

import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString()
public class ModuleCardsDTO {
    private Module module;
    private Card card;
    private List<Card> cardList;

    private int responseCode;
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
                case 11:
                    message = "Such module is exist";
                    break;
                case 12:
                    message = "";
                    break;
                case 13:
                    message = "";
                    break;
            }
            this.message = message;
        }
    }

    public String getErrorInfo(){
        if(responseCode == 0){
            return "";
        }else{
            return "error code="+responseCode+"("+message+")";
        }
    }
}
