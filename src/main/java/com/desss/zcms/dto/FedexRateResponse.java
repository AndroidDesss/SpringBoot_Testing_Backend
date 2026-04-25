package com.desss.zcms.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class FedexRateResponse {
    private String service;
    private Double amount;
}
