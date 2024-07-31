package com.xxharutoxx.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmedPassword;
    private String firstname;
    private String lastname;
    private String cellphone;
    private String country;
}
