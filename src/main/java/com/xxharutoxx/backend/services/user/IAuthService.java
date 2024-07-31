package com.xxharutoxx.backend.services.user;

import com.xxharutoxx.backend.dto.request.LoginRequest;
import com.xxharutoxx.backend.dto.request.RegisterRequest;
import com.xxharutoxx.backend.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
