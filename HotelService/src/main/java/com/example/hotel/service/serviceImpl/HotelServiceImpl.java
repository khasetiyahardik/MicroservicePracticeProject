package com.example.hotel.service.serviceImpl;

import com.example.hotel.service.dto.AddHotelDTO;
import com.example.hotel.service.dto.GetAllRatingsOfHotelDTO;
import com.example.hotel.service.dto.ResponseDTO;
import com.example.hotel.service.entity.HotelEntity;
import com.example.hotel.service.repository.HotelRepository;
import com.example.hotel.service.service.HotelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic}")
    String topic;

    @Override
    public ResponseDTO addHotel(AddHotelDTO addHotelDTO) {
        Optional<HotelEntity> existingHotel = hotelRepository.findByHotelName(addHotelDTO.getName());
        if (existingHotel.isEmpty()) {
            HotelEntity hotelEntity = new HotelEntity();
            BeanUtils.copyProperties(addHotelDTO, hotelEntity);
            hotelRepository.save(hotelEntity);
            kafkaTemplate.send(addHotelDTO.toString(), topic);

            return new ResponseDTO("200", "Hotel added successfully", null);
        } else {
            return new ResponseDTO("400", "Hotel having same name is already registered : ", addHotelDTO.getName());
        }
    }

    @Override
    public ResponseDTO getHotelById(Long id) {
        Optional<HotelEntity> hotelEntity = hotelRepository.findById(id);
        if (hotelEntity.isPresent()) {
            return new ResponseDTO("200", "Data fetched successfully", hotelEntity.get());
        } else {
            return new ResponseDTO("404", "Hotel not found", null);
        }
    }

    @Override
    public ResponseDTO getAllHotels() {
        List<HotelEntity> hotelEntityList = hotelRepository.findAll();
        if (hotelEntityList.size() != 0) {
            return new ResponseDTO("200", "Data fetched successfully", hotelEntityList);
        } else {
            return new ResponseDTO("404", "No hotels available", null);
        }
    }

    @Override
    public ResponseDTO deleteHotelById(Long id) {
        Optional<HotelEntity> hotelEntity = hotelRepository.findById(id);
        if (hotelEntity.isPresent()) {
            hotelRepository.delete(hotelEntity.get());
            return new ResponseDTO("200", "Hotel deleted successfully", null);
        } else {
            return new ResponseDTO("400", "Hotel not found", null);
        }
    }

    @Override
    public Object getAllRatingsOfHotel(Long hotelId) {
        Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);
        if (hotelEntity.isPresent()) {
            Map<GetAllRatingsOfHotelDTO, String> ratings = restTemplate.getForObject("http://RATING-SERVICE/rating/getAllRatingsOfHotel/" + hotelId, Map.class);
            return ratings;
        } else {
            return new ResponseDTO("404", "Hotel not found", null);
        }
    }

}
