package com.gosoft.ecommerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.ecommerce.dto.LoginUserDto;
import com.gosoft.ecommerce.dto.RegisterUserDto;
import com.gosoft.ecommerce.entity.User;
import com.gosoft.ecommerce.response.LoginResponse;
import com.gosoft.ecommerce.service.AuthenticationService;
import com.gosoft.ecommerce.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        Map<String, Object> map = new ObjectMapper().convertValue(authenticatedUser, new TypeReference<>() {
        });
        String jwtToken = jwtService.generateToken(map,authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken,jwtService.getExpirationTime(), authenticatedUser.getRole());

        return ResponseEntity.ok(loginResponse);
    }
}