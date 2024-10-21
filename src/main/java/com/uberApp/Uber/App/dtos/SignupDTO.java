package com.uberApp.Uber.App.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SignupDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String name;
    private String email;
    private String password;
}