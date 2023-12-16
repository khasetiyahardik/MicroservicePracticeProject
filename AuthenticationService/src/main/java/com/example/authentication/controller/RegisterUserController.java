package com.example.authentication.controller;

import com.example.authentication.dto.ResponseDTO;
import com.example.authentication.entity.AuthenticationEntity;
import com.example.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterUserController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/saveUser")
    public ResponseDTO saveUser(@RequestBody AuthenticationEntity authenticationEntity){
        return authenticationService.saveUser(authenticationEntity);
    }
}
