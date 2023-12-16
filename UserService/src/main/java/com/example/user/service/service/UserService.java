package com.example.user.service.service;

import com.example.user.service.dto.EsHotelInfo;
import com.example.user.service.dto.LoginDTO;
import com.example.user.service.dto.ResponseDTO;
import com.example.user.service.dto.SaveUserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;

public interface UserService {
    ResponseDTO saveUser(SaveUserDTO saveUserDTO);

    ResponseDTO getUserById(Long id);

    ResponseDTO getAllUsers();

    Object getAllRatingsByUserId(Long userId);

    ResponseDTO login(LoginDTO loginDTO);

    void listen(String message) throws JsonProcessingException;

    ArrayList<EsHotelInfo> searchHotelByName(String name) throws JsonProcessingException;
}
