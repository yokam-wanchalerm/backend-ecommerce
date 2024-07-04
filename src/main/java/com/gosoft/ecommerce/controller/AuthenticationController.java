package com.gosoft.ecommerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.ecommerce.dto.LoginUserDto;
import com.gosoft.ecommerce.dto.RegisterUserDto;
import com.gosoft.ecommerce.entity.User;
import com.gosoft.ecommerce.enums.Type;
import com.gosoft.ecommerce.response.GlobalResponse;
import com.gosoft.ecommerce.response.LoginResponse;
import com.gosoft.ecommerce.service.AuthenticationService;
import com.gosoft.ecommerce.service.JwtService;
import com.gosoft.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<?>> register(@RequestBody RegisterUserDto registerUserDto) {
        Optional<User> exist = userService.getUserByEmail(registerUserDto.getEmail());

        if (exist.isPresent()) {
            return new ResponseEntity<>(new GlobalResponse<>(Type.EMAIL_EXIST, registerUserDto.getEmail()), HttpStatus.BAD_REQUEST);
        }

        User registeredUser = authenticationService.signup(registerUserDto);
        GlobalResponse<User> response = new GlobalResponse<>(registeredUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<?>> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        Map<String, Object> map = new ObjectMapper().convertValue(authenticatedUser, new TypeReference<>() {
        });
        String jwtToken = jwtService.generateToken(map, authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        GlobalResponse<LoginResponse> response = new GlobalResponse<>(loginResponse);
        return ResponseEntity.ok(response);
    }
}