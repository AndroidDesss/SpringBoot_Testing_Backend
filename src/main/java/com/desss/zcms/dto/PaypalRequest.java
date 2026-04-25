package com.desss.zcms.dto;

import lombok.Data;

@Data
public class PaypalRequest {
    private Double amount;
    private String currency;
}
