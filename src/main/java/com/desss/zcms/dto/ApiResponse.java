package com.desss.zcms.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    boolean success;
    String message;
    T data;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = true;
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> ok(String msg, T data) {
        ApiResponse<T> r = ok(data);
        r.message = msg;
        return r;
    }

    public static <T> ApiResponse<T> error(String msg) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = false;
        r.message = msg;
        return r;
    }
}
