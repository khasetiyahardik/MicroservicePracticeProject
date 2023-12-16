package com.example.user.service.dto;

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
