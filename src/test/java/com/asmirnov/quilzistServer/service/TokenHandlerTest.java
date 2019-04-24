package com.asmirnov.quilzistServer.service;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class TokenHandlerTest {
    @Test
    public void generateToken(){
        TokenHandler tokenHandler = new TokenHandler();
        String token = tokenHandler.generateAccessToken(21L, LocalDateTime.now().plusDays(1));
        System.out.println("token="+token);

        Optional<Long> id = tokenHandler.extractUserId(token);
        System.out.println("decoded id="+id.get().toString());
    }

}