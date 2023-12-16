package com.example.hotel.service.service;

import com.example.hotel.service.dto.AddHotelDTO;
import com.example.hotel.service.dto.ResponseDTO;

public interface HotelService {

    ResponseDTO addHotel(AddHotelDTO addHotelDTO);

    ResponseDTO getHotelById(Long id);

    ResponseDTO getAllHotels();

    ResponseDTO deleteHotelById(Long id);

    Object getAllRatingsOfHotel(Long hotelId);
}
