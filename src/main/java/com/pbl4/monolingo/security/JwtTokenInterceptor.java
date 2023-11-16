package com.pbl4.monolingo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy token từ session
        String token = (String) request.getSession().getAttribute("jwtToken");
        if (token != null) {
            // Thêm token vào header của request
            response.addHeader("Authorization", "Bearer " + token);
        }
        return true;

    }
}