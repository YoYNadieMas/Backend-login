package com.xxharutoxx.backend.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String getToken(UserDetails userDetails);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
