package com.example.authentication.serviceImpl;

import com.example.authentication.entity.AuthenticationEntity;
import com.example.authentication.repository.AuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserService")
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        logger.info("CustomAdminDetailService : loadUserByUsername");
        AuthenticationEntity authentication = null;
        AuthenticationEntity existingData = authenticationRepository.findByName(name);
        System.out.println(existingData);
        if (existingData != null) {
            authentication = existingData;
        }
        if (existingData == null) {
            System.out.println("User not found");
        }
        if (authentication.getName() == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails user = User.withUsername(authentication.getName()).password(authentication.getPassword()).authorities("User").build();
        return user;
    }
}
