package com.desss.zcms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpsRateResponse {
    private String serviceCode;
    private String serviceName;
    private Double amount;
}
