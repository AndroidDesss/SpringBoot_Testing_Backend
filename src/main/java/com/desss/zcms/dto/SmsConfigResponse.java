package com.desss.zcms.dto;

import lombok.Data;

@Data
public class SmsConfigResponse {
    private Long id;
    private Long websiteId;
    private String accountSid; // masked
    private String twilioNumber;
    private Integer status;
}