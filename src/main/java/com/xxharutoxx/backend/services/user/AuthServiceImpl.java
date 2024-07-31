package com.xxharutoxx.backend.services.user;

import com.xxharutoxx.backend.dto.request.LoginRequest;
import com.xxharutoxx.backend.dto.request.RegisterRequest;
import com.xxharutoxx.backend.dto.response.AuthResponse;
import com.xxharutoxx.backend.entities.User;
import com.xxharutoxx.backend.exceptions.ResourceNotFoundException;
import com.xxharutoxx.backend.persistences.IUserRepository;
import com.xxharutoxx.backend.services.jwt.IJwtService;
import com.xxharutoxx.backend.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository iUserRepository;
    private final IJwtService iJwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetails userDetails = iUserRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
            String token = iJwtService.getToken(userDetails);
            return AuthResponse.builder().token(token).build();
        }catch (BadCredentialsException e){
            throw new ResourceNotFoundException("Credenciales incorrectas");
        }
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmedPassword())) {
            throw new ResourceNotFoundException("Las contraseñas no coinciden");
        }
        if (iUserRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new ResourceNotFoundException("El usuario ya existe");
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .cellphone(registerRequest.getCellphone())
                .country(registerRequest.getCountry())
                .role(Role.USER)
                .build();

        iUserRepository.save(user);
        return AuthResponse.builder().token(iJwtService.getToken(user)).build();
    }
}
