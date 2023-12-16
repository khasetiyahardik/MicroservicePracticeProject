package com.example.authentication.controller;

import com.example.authentication.dto.JwtRequest;
import com.example.authentication.dto.ResponseDTO;
import com.example.authentication.entity.AuthenticationEntity;
import com.example.authentication.repository.AuthenticationRepository;
import com.example.authentication.serviceImpl.CustomUserDetailService;
import com.example.authentication.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@RestController
public class AuthenticationController {

    public static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticate;

    @Resource(name = "customUserService")
    private CustomUserDetailService userDetailsService;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;




    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseDTO createAuthenticationToken(@RequestBody AuthenticationEntity authenticationRequest, Errors errors)
            throws Exception {
        if (errors.hasErrors()) {
            return new ResponseDTO("400", errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {

            try {

                authenticate(authenticationRequest.getName(), authenticationRequest.getPassword());
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getName());
                final String token = jwtTokenUtil.generateTokenFromUsername(userDetails.getUsername());
                Optional<AuthenticationEntity> authenticationEntity = authenticationRepository.findOneByName(authenticationRequest.getName());

                if (authenticationEntity.isPresent()) {
                    AuthenticationEntity authentication = authenticationEntity.get();
                    if (authentication.getIsVerified() == Boolean.FALSE) {
                        return new ResponseDTO("400", "User not verified", null);
                    }
                    if (authentication != null && authentication.getIsVerified().equals(true)) {
                        return new ResponseDTO("200", "Login successfully", token);
                    }
                }
                return new ResponseDTO("200", "Login successfully", token);
            } catch(Exception e) {
                if (e.getMessage().equalsIgnoreCase("Invalid credentials")) {

                }
            }
            return new ResponseDTO("400","Invalid credentials" , null);
        }
    }
    private void authenticate(String name, String password) throws Exception {
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);

        try {
            System.out.println(name+password);
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }

}

