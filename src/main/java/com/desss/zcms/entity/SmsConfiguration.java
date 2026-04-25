package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sms_configuration")
@Data
public class SmsConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long websiteId;

    @Column(length = 500)
    private String accountSid;

    @Column(length = 500)
    private String authToken;

    private String twilioNumber;

    private Integer status; // 1 = active, 0 = inactive
}