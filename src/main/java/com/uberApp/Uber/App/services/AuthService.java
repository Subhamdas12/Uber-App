package com.uberApp.Uber.App.services;

import com.uberApp.Uber.App.dtos.DriverDTO;
import com.uberApp.Uber.App.dtos.SignupDTO;
import com.uberApp.Uber.App.dtos.UserDTO;

public interface AuthService {

    UserDTO signUp(SignupDTO signupDTO);

    DriverDTO onBoardNewDriver(Long userId, String vehicleId);

    String[] login(String email, String password);

}
