package com.desss.zcms.dto;

import lombok.Data;

@Data
public class LogoutRequest {
    String accessToken;
    String refreshToken;
}
