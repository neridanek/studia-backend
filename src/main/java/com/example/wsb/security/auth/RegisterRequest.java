package com.example.wsb.security.auth;


import com.example.wsb.user.Role;
import com.example.wsb.validation.CheckRegisterRequest;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CheckRegisterRequest
@Getter
@Setter
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private Role role;

}
