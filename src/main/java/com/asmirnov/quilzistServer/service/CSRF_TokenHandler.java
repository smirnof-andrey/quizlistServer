package com.asmirnov.quilzistServer.service;

import org.springframework.security.web.csrf.CsrfToken;

public class CSRF_TokenHandler {
    public CSRF_TokenHandler() {
        CsrfToken csrfToken = new CsrfToken() {
            @Override
            public String getHeaderName() {
                return null;
            }

            @Override
            public String getParameterName() {
                return null;
            }

            @Override
            public String getToken() {
                return null;
            }
        };
    }
}
