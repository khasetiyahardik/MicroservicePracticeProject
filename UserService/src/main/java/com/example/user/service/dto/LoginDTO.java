package com.example.user.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDTO {
    private String name;
    private String password;
}
