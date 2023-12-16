package com.example.rating.service.serviceImpl;

import com.example.rating.service.document.Rating;
import com.example.rating.service.dto.AddRatingDTO;
import com.example.rating.service.dto.ResponseDTO;
import com.example.rating.service.repository.RatingRepository;
import com.example.rating.service.service.RatingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepository;


    @Override
    public ResponseDTO addRating(AddRatingDTO addRatingDTO) {
        Rating rating = new Rating();
        BeanUtils.copyProperties(addRatingDTO, rating);
        ratingRepository.save(rating);
        return new ResponseDTO("200", "Ratings added successfully",null);
    }

    @Override
    public ResponseDTO getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        if (ratings.size() != 0){
            return new ResponseDTO("200","Ratings fetched successfully",ratings);
        }else {
            return new ResponseDTO("404","No ratings available",null);
        }
    }

    @Override
    public ResponseDTO getAllRatingsByUserId(Long userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        if (ratings.size() != 0){
            return new ResponseDTO("200", "Ratings fetched successfully",ratings);
        }else {
            return new ResponseDTO("404","No ratings under this user",null);
        }
    }

    @Override
    public ResponseDTO getAllRatingsOfHotel(Long hotelId) {
        List<Rating> ratings = ratingRepository.findByHotelId(hotelId);
        if (ratings.size() != 0){
            return new ResponseDTO("200", "Ratings fetched successfully",ratings);
        }else {
            return new ResponseDTO("404","No ratings are available on this hotel",null);
        }
    }

    @Override
    public ResponseDTO getAllRatingsHavingSameNumberOfRatings(Integer rating) {
        List<Rating> ratings = ratingRepository.findByRating(rating);
        if (ratings.size() != 0){
            return new ResponseDTO("200","Ratings fetched successfully",ratings);
        }else {
            return new ResponseDTO("404","No ratings found",null);
        }
    }
}
