package com.uberApp.Uber.App.advices;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

/**
 * ApiError
 */
@Data
@Builder
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<String> subErrors;
}