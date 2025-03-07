package com.example.bankSphere.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. Skip filtering for public endpoints like /api/test/**.
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/api/test/")) {
            // Simply pass the request to the next filter in the chain
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 2. Extract and validate JWT (not implemented here - replace with your own logic).
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Strip "Bearer " prefix
            if (isTokenValid(token)) {
                // Add user details to SecurityContext, if required
            }
        } else {
            // Optionally handle missing or invalid tokens
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Continue the filter chain if everything is valid
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isTokenValid(String token) {
        // Add your JWT validation logic here (e.g., verify signature, expiration, etc.)
        return true; // For now, always valid
    }
}
