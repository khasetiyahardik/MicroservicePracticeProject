package com.example.hotel.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddHotelDTO {
    private String name;
    private String location;
    private String about;
}
