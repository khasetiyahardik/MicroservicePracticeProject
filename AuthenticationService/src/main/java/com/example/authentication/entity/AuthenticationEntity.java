package com.example.authentication.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationEntity {
    @Id
    private String name;
    private String password;
    private Boolean isVerified;
}
