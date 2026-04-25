package com.desss.zcms.dto;

import lombok.Data;

@Data
public class SmsSendRequest {
    private Long websiteId;
    private String mobileNumber;
    private String message;
}