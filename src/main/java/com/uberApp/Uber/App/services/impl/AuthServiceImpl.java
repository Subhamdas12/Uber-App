package com.uberApp.Uber.App.services.impl;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.SignupDTO;
import com.uberApp.Uber.App.dtos.UserDTO;
import com.uberApp.Uber.App.entities.DriverEntity;
import com.uberApp.Uber.App.entities.UserEntity;
import com.uberApp.Uber.App.entities.enums.Role;
import com.uberApp.Uber.App.exceptions.ResourceNotFoundException;
import com.uberApp.Uber.App.exceptions.RuntimeConflictException;
import com.uberApp.Uber.App.repositories.UserRepository;
import com.uberApp.Uber.App.security.JWTService;
import com.uberApp.Uber.App.services.AuthService;
import com.uberApp.Uber.App.services.DriverService;
import com.uberApp.Uber.App.services.RiderService;
import com.uberApp.Uber.App.services.WalletService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final DriverService driverService;
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    @Transactional
    public UserDTO signUp(SignupDTO signupDTO) {
        UserEntity user = userRepository.findByEmail(signupDTO.getEmail()).orElse(null);
        if (user != null)
            throw new RuntimeConflictException(
                    "Cannot signup , User already exists with email : " + signupDTO.getEmail());
        UserEntity mappedUser = modelMapper.map(signupDTO, UserEntity.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        UserEntity savedUser = userRepository.save(mappedUser);

        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onBoardNewDriver(Long userId, String vehicleId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        if (user.getRoles().contains(Role.DRIVER)) {
            throw new RuntimeConflictException("User with id " + userId + " is already a Driver");
        }
        DriverEntity createDriver = DriverEntity.builder().user(user).rating(0.0).vehicleId(vehicleId).isAvailable(true)
                .build();

        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        DriverEntity savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[] { accessToken, refreshToken };
    }
}
