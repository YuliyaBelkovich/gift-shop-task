package com.epam.esm.controller;

import com.epam.esm.dto.request.LoginRequest;
import com.epam.esm.dto.request.SignupRequest;
import com.epam.esm.utils.JwtUtils;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/gift-shop/auth")
public class AuthController {

    UserService userService;
    RoleService roleService;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService, RoleService roleService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.logIn(loginRequest));
    }

//    @GetMapping("/user")
//    public static Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        return Collections.singletonMap("name", principal.getAttribute("name"));
//    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        userService.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
