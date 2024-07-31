package com.xxharutoxx.backend.controllers;

import com.xxharutoxx.backend.dto.request.LoginRequest;
import com.xxharutoxx.backend.dto.request.RegisterRequest;
import com.xxharutoxx.backend.dto.response.AuthResponse;
import com.xxharutoxx.backend.error.ErrorResponse;
import com.xxharutoxx.backend.exceptions.ResourceNotFoundException;
import com.xxharutoxx.backend.services.user.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService iAuthService;
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            return ResponseEntity.ok(iAuthService.login(loginRequest));
        } catch (ResourceNotFoundException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
    @PostMapping(value = "register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        try {
            return ResponseEntity.ok(iAuthService.register(registerRequest));
        }catch (ResourceNotFoundException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
