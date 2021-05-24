package com.epam.esm.controller;

import com.epam.esm.dto.request.LoginRequest;
import com.epam.esm.dto.request.SignupRequest;
import com.epam.esm.service.UserService;
import com.epam.esm.service.auth.AuthenticationManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/gift-shop/auth")
public class AuthController {

    private AuthenticationManagerHelper authenticationManagerHelper;

    @Autowired
    public AuthController(AuthenticationManagerHelper authenticationManagerHelper) {
        this.authenticationManagerHelper = authenticationManagerHelper;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationManagerHelper.logIn(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authenticationManagerHelper.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
