package com.example.rating.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddRatingDTO {

    private Long userId;
    private Long hotelId;
    private Integer rating;
    private String feedback;
}
