package com.example.hotel.service.controller;

import com.example.hotel.service.dto.AddHotelDTO;
import com.example.hotel.service.dto.ResponseDTO;
import com.example.hotel.service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelService hotelService;



    @PostMapping("/add")
    public ResponseDTO addHotel(@RequestBody AddHotelDTO addHotelDTO){
        return hotelService.addHotel(addHotelDTO);
    }

    @GetMapping("/getById/{id}")
    public ResponseDTO getHotelById(@PathVariable("id")Long id){
        return hotelService.getHotelById(id);
    }

    @GetMapping("/getAll")
    public ResponseDTO getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/getAllRatingsOfHotel/{id}")
    public Object getAllRatingsOfHotel(@PathVariable("id")Long hotelId){
        return hotelService.getAllRatingsOfHotel(hotelId);
    }

    @DeleteMapping("/deleteHotelById/{id}")
    public ResponseDTO deleteHotelById(@PathVariable("id")Long id){
        return hotelService.deleteHotelById(id);
    }
}
