package com.example.authentication.serviceImpl;

import com.example.authentication.dto.ResponseDTO;
import com.example.authentication.entity.AuthenticationEntity;
import com.example.authentication.repository.AuthenticationRepository;
import com.example.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO saveUser(AuthenticationEntity authenticationEntity) {
        authenticationEntity.setPassword(passwordEncoder.encode(authenticationEntity.getPassword()));
        authenticationRepository.save(authenticationEntity);
        return new ResponseDTO("200","user saved in auth table",null);
    }
}
