package com.example.bankSphere.security;

import com.example.bankSphere.service.UserSecretKeyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.Base64;

@Component
public class JwtRequestFilter implements Filter {

    @Autowired
    private UserSecretKeyService userSecretKeyService;  // Inject UserSecretKeyService

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. Skip filtering for public endpoints like /api/test/** or /api/auth/register
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/api/test/") || requestUri.equals("/api/auth/register")) {
            // Simply pass the request to the next filter in the chain
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 2. Extract and validate JWT
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            System.out.println("Received token: " + token);
            token = token.substring(7); // Strip "Bearer " prefix

            // Retrieve username from token
            String username = extractUsernameFromToken(token);
            System.out.println("Extracted username: " + username);

            if (username != null) {
                // Retrieve the secret key from the database for this user
                String secretKeyBase64 = userSecretKeyService.retrieveSecretKey(username);
                System.out.println("Retrieved secret key (Base64): " + secretKeyBase64);

                if (secretKeyBase64 != null) {
                    byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyBase64);  // Decode base64 secret key
                    Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);  // Rebuild the key from base64 bytes
                    System.out.println("Decoded secret key length: " + secretKeyBytes.length);

                    if (isTokenValid(token, secretKey)) {
                        // Token is valid, you can add the user to the security context here
                        System.out.println("Token is valid");
                        // Optionally add username or user details to the SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
                    } else {
                        System.out.println("Invalid or expired token.");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid or expired token.");
                        return;
                    }
                } else {
                    System.out.println("Secret key not found for user: " + username);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Secret key not found for user.");
                    return;
                }
            } else {
                System.out.println("Invalid token - Unable to extract username.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token.");
                return;
            }
        } else {
            System.out.println("Authorization header is missing or invalid.");
            // If the token is missing or doesn't start with "Bearer"
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization header is missing or invalid.");
            return;
        }

        // Continue the filter chain if everything is valid
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String extractUsernameFromToken(String token) {
        try {
            // Manually split the token into its parts (header, payload, signature)
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid token format");
            }

            // Decode the payload (which is the second part) using Base64 URL decoding
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            System.out.println("Decoded payload: " + payloadJson);

            // Parse the JSON to extract the username claim
            ObjectMapper mapper = new ObjectMapper();
            JsonNode payloadNode = mapper.readTree(payloadJson);
            String username = payloadNode.has("username") ? payloadNode.get("username").asText() : null;
            System.out.println("Extracted username (from payload): " + username);

            if (username == null) {
                throw new IllegalArgumentException("Username claim not found in token.");
            }

            // Retrieve the secret key for this user from the database
            String secretKeyBase64 = userSecretKeyService.retrieveSecretKey(username);
            if (secretKeyBase64 == null) {
                throw new IllegalArgumentException("Secret key not found for user: " + username);
            }
            System.out.println("Retrieved secret key (Base64): " + secretKeyBase64);

            // Decode the secret key and prepare it for verification
            byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyBase64);
            System.out.println("Decoded secret key length: " + secretKeyBytes.length);
            Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

            // Now, validate the token with the correct secret key
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            // If validation succeeds, return the extracted username
            return username;

        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired.");
        } catch (JwtException e) {
            System.out.println("JWT error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error extracting username from token: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }



    // Validate the token using the secret key
    private boolean isTokenValid(String token, Key secretKey) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)  // Use the provided secret key
                    .build()
                    .parseClaimsJws(token);  // Parse and validate the JWT token

            return true;

        } catch (ExpiredJwtException e) {
            // Token has expired
            System.out.println("Token has expired");
        } catch (JwtException e) {
            // Invalid token (signature mismatch or malformed token)
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            System.out.println("Error while validating token: " + e.getMessage());
        }

        return false;
    }

}
