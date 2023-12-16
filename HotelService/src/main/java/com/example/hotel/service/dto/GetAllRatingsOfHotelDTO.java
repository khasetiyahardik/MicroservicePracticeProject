package com.example.hotel.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllRatingsOfHotelDTO {
    private String ratingId;
    private Long userId;
    private Long hotelId;
    private Integer rating;
    private String feedback;
}
