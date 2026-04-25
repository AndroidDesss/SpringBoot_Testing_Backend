package com.desss.zcms.dto;

import lombok.Data;

@Data
public class SmsConfigRequest {
    private Long websiteId;
    private String accountSid;
    private String authToken;
    private String twilioNumber;
    private Integer status = 1;
}