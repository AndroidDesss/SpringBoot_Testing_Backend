package com.desss.zcms.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    Long websiteId;
    Double amount;
    String cardNumber;
    String expiry; // format: YYYY-MM
    String cvv;
}
