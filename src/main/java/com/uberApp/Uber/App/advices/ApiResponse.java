package com.uberApp.Uber.App.advices;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * ApiResponse
 */
@Data
public class ApiResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this.error = error;
    }

}