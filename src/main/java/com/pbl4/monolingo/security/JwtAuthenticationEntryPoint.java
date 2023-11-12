package com.pbl4.monolingo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(isPreAuthentication(request)){
            response.sendRedirect("/public/login");
        }
        else {
//            response.sendRedirect("/public/error");
        }
    }
    private boolean isPreAuthentication(HttpServletRequest request) {

        // Implement your logic to check whether this is a pre-authentication request

        // You might check if there's no Authorization header or if the session is new
        String token = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("jwtToken")){
                    token = cookie.getValue();
                }
            }
        }


        // For example:

        return request.getHeader("Authorization") == null && token.equals("");

    }
}
