package com.example.hotel.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDTO {
    private String status;
    private String message;
    private Object data;
}
