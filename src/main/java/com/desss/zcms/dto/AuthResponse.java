package com.desss.zcms.dto;

import lombok.Data;

@Data
public class AuthResponse {
    String accessToken;
    String refreshToken;
    String tokenType = "Bearer";
    Long expiresIn;
    UserInfo user;

    @Data
    public static class UserInfo {
        Long id;
        String username;
        String fullName;
        String email;
        String role;
        Long websiteId;
    }
}
