package com.desss.zcms.dto;

import lombok.Data;

@Data
public class MailSendRequest {
    private Long websiteId;
    private String toEmail;
    private String subject;
    private String body;
}
