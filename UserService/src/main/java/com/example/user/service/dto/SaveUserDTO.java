package com.example.user.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveUserDTO {
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String gender;
    private String about;
}
