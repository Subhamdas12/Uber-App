package com.uberApp.Uber.App.dtos;

import java.util.Set;

import com.uberApp.Uber.App.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private Set<Role> roles;
}