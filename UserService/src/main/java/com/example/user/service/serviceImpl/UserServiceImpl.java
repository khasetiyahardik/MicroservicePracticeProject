package com.example.user.service.serviceImpl;

import com.example.user.service.dto.GetRatingsDTO;
import com.example.user.service.dto.LoginDTO;
import com.example.user.service.dto.ResponseDTO;
import com.example.user.service.dto.SaveUserDTO;
import com.example.user.service.dto.EsHotelInfo;
import com.example.user.service.dto.elastic.EsQueryBuilderDTO;
import com.example.user.service.dto.elastic.HitsDTO;
import com.example.user.service.entity.UserEntity;
import com.example.user.service.reporsitory.UserRepository;
import com.example.user.service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public ResponseDTO saveUser(SaveUserDTO saveUserDTO) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(saveUserDTO.getEmail());
        if (existingUser.isEmpty()) {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(saveUserDTO, userEntity);
            userEntity.setIsVerified(false);
            userRepository.save(userEntity);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<UserEntity> entity = new HttpEntity(userEntity,headers);
            restTemplate.exchange("http://AUTHENTICATION-SERVICE/auth/saveUser", HttpMethod.POST, entity,ResponseDTO.class);
            return new ResponseDTO("200", "User saved successfully", null);
        } else {
            return new ResponseDTO("400", "User having same email is already registered : ", saveUserDTO.getEmail());
        }
    }

    @Override
    public ResponseDTO getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseDTO("200", "Data fetched successfully", user.get());
        } else {
            return new ResponseDTO("404", "User not found", null);
        }
    }

    @Override
    public ResponseDTO getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        if (userEntityList.size() != 0) {
            return new ResponseDTO("200", "Data fetched successfully", userEntityList);
        } else {
            return new ResponseDTO("404", "No users available", null);
        }
    }

    @Override
    public Object getAllRatingsByUserId(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
           Map<GetRatingsDTO,String> object =  restTemplate.getForObject("http://RATING-SERVICE/rating/getAllRatingsByUserId/" + userId, Map.class);
           return object;
        }else {
            return new ResponseDTO("404", "User not found", null);
        }
    }

    @Override
    public ResponseDTO login(LoginDTO loginDTO) {
        Optional<UserEntity> user = userRepository.findByName(loginDTO.getName());
        if (user.isEmpty()){
            return new ResponseDTO("404","User not found",null);
        }else {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<UserEntity> entity = new HttpEntity(loginDTO,headers);
            ResponseDTO object = restTemplate.exchange("http://AUTHENTICATION-SERVICE/authenticate",HttpMethod.POST, entity,ResponseDTO.class).getBody();
            return object;
        }
    }

    @Override
    @KafkaListener(topics = "hotel")
    public void listen(String message) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        EsHotelInfo esHotelInfo = objectMapper.readValue(message, EsHotelInfo.class);
        HttpEntity<EsHotelInfo> httpEntity = new HttpEntity<>(esHotelInfo, headers);
        restTemplate.exchange("http://localhost:9200/hotel-rating-index-000001/_doc", HttpMethod.POST, httpEntity, Map.class);
        System.out.println(message);
    }


    @Override
    public ArrayList<EsHotelInfo> searchHotelByName(String name) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        EsQueryBuilderDTO esQueryBuilder = new EsQueryBuilderDTO();
        String query = esQueryBuilder.searchByHotelName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(query, headers);
        Map data = (Map) restTemplate.exchange("http://localhost:9200/hotel-rating-index-000001/_search", HttpMethod.POST, httpEntity, Map.class).getBody().get("hits");
        String dataTestString = objectMapper.writeValueAsString(data);
        HitsDTO hitsDTO = objectMapper.readValue(dataTestString, HitsDTO.class);
        ArrayList<EsHotelInfo> esHotelInfos = new ArrayList<>();
        hitsDTO.getHits().stream().forEach(p -> esHotelInfos.add(p.get_source()));
        return esHotelInfos;
    }


}
