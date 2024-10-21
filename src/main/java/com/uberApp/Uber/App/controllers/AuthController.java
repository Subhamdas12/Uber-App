package com.uberApp.Uber.App.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uberApp.Uber.App.dtos.SignupDTO;
import com.uberApp.Uber.App.dtos.UserDTO;
import com.uberApp.Uber.App.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO) {
        return new ResponseEntity<>(authService.signUp(signupDTO), HttpStatus.CREATED);
    }
}
