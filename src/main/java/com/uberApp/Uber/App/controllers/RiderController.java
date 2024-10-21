package com.uberApp.Uber.App.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uberApp.Uber.App.dtos.RideRequestDTO;
import com.uberApp.Uber.App.services.RiderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/riders")
@RequiredArgsConstructor
public class RiderController {
    private final RiderService riderService;

    @PostMapping
    public ResponseEntity<RideRequestDTO> requestRide(@RequestBody RideRequestDTO rideRequestDTO) {
        return new ResponseEntity<>(riderService.requestRide(rideRequestDTO), HttpStatus.OK);
    }
}
