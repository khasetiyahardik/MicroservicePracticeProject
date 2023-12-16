package com.example.rating.service.controller;

import com.example.rating.service.dto.AddRatingDTO;
import com.example.rating.service.dto.ResponseDTO;
import com.example.rating.service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping("/addRating")
    public ResponseDTO addRating(@RequestBody AddRatingDTO addRatingDTO){
        return ratingService.addRating(addRatingDTO);
    }

    @GetMapping("/getAllRatings")
    public ResponseDTO getAllRatings(){
        return ratingService.getAllRatings();
    }

    @GetMapping("/getAllRatingsByUserId/{id}")
    public ResponseDTO getAllRatingsByUserId(@PathVariable("id")Long userId){
        return ratingService.getAllRatingsByUserId(userId);
    }

    @GetMapping("/getAllRatingsOfHotel/{id}")
    public ResponseDTO getAllRatingsOfHotel(@PathVariable("id")Long hotelId){
        return ratingService.getAllRatingsOfHotel(hotelId);
    }

    @GetMapping("/getAllRatingsHavingSameNumberOfRatings/{id}")
    public ResponseDTO getAllRatingsHavingSameNumberOfRatings(@PathVariable("id")Integer rating){
        return ratingService.getAllRatingsHavingSameNumberOfRatings(rating);
    }
}
