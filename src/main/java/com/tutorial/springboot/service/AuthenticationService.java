package com.tutorial.springboot.service;

import com.tutorial.springboot.enums.ERole;
import com.tutorial.springboot.model.Role;
import com.tutorial.springboot.model.User;
import com.tutorial.springboot.repository.UserRepository;
import com.tutorial.springboot.request.LoginRequest;
import com.tutorial.springboot.request.SignUpRequest;
import com.tutorial.springboot.response.AuthenticationResponse;
import com.tutorial.springboot.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signup(SignUpRequest signUpRequest) {
        Role role = new Role();
        role.setERole(ERole.USER);
        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(signUpRequest.getPassword()))
                .role(role)
                .build();
        log.info(user.toString());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
