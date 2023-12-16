package com.example.user.service.controller;

import com.example.user.service.dto.EsHotelInfo;
import com.example.user.service.dto.LoginDTO;
import com.example.user.service.dto.ResponseDTO;
import com.example.user.service.dto.SaveUserDTO;
import com.example.user.service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseDTO saveUser(@RequestBody SaveUserDTO saveUserDTO) {
        return userService.saveUser(saveUserDTO);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseDTO getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    public ResponseDTO getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getAllRatingsByUserId/{id}")
//    @CircuitBreaker(name = "user_rating_circuitBreaker", fallbackMethod = "user_rating_fallback")
    @RateLimiter(name = "nameOfRateLimiter", fallbackMethod = "fallbackMethod")
    public Object getAllRatingsByUserId(@PathVariable("id")Long userId){
        return userService.getAllRatingsByUserId(userId);
    }

    public Object user_rating_fallback(Long userId, Exception ex){
        return new ResponseDTO("200","Fallback method called due to getAllRatingsByUserId is down.",null);
    }

    @GetMapping("/searchHotelByName")
    public ArrayList<EsHotelInfo> searchHotelByName(@RequestParam String name) throws JsonProcessingException {
        return userService.searchHotelByName(name);
    }
}
