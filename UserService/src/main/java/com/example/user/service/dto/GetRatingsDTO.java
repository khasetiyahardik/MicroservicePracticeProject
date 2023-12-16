package com.example.user.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRatingsDTO {
    private String ratingId;
    private Long userId;
    private Long hotelId;
    private Integer rating;
    private String feedback;
}
