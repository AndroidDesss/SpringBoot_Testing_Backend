package com.desss.zcms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UspsRateResponseDto {
    private String service;
    private Double price;
}
