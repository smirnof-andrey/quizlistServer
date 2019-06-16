package com.asmirnov.quilzistServer.security;

import org.springframework.security.web.csrf.CsrfToken;

public class CsrfTokenHandler {
    public CsrfTokenHandler() {
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
