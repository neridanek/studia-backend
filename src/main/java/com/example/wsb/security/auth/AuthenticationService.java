package com.example.wsb.security.auth;

import com.example.wsb.security.config.JwtService;
import com.example.wsb.user.Role;
import com.example.wsb.user.User;
import com.example.wsb.user.UserRepository;
import com.example.wsb.user.applicant.Applicant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request){
        User user = Applicant.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.valueOf(String.valueOf(request.getRole())))
                .build();
        userRepository.saveAndFlush(user);


        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception{

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),request.getPassword()));


        var user = userRepository.findUserByLogin(request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }
}
