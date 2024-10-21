package com.uberApp.Uber.App.services.impl;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.dtos.SignupDTO;
import com.uberApp.Uber.App.dtos.UserDTO;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.enums.Role;
import com.uberApp.Uber.App.exceptions.RuntimeConflictException;
import com.uberApp.Uber.App.repositories.UserRepository;
import com.uberApp.Uber.App.services.AuthService;
import com.uberApp.Uber.App.services.RiderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;

    @Override
    @Transactional
    public UserDTO signUp(SignupDTO signupDTO) {
        UserEntity user = userRepository.findByEmail(signupDTO.getEmail()).orElse(null);
        if (user != null)
            throw new RuntimeConflictException(
                    "Cannot signup , User already exists with email : " + signupDTO.getEmail());
        UserEntity mappedUser = modelMapper.map(signupDTO, UserEntity.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        UserEntity savedUser = userRepository.save(mappedUser);

        riderService.createNewRider(savedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

}
