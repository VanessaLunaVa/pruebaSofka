package com.example.transacciones.util;

import java.time.LocalDateTime;

public record GeneralResponse<T>(boolean success, String message, T data, Object errors, LocalDateTime timestamp) {

    public static <T> GeneralResponse<T> ok(String message, T data) {
        return new GeneralResponse<>(true, message, data, null, LocalDateTime.now());
    }

    public static <T> GeneralResponse<T> error(String message, Object errors) {
        return new GeneralResponse<>(false, message, null, errors, LocalDateTime.now());
    }
}