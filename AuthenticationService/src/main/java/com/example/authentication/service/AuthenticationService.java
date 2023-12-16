package com.example.authentication.service;

import com.example.authentication.dto.ResponseDTO;
import com.example.authentication.entity.AuthenticationEntity;

public interface AuthenticationService {
    ResponseDTO saveUser(AuthenticationEntity authenticationEntity);
}
