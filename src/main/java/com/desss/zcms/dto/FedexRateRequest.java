package com.desss.zcms.dto;

import lombok.*;

@Data
public class FedexRateRequest {
    private String fromZip;
    private String toZip;
    private Double weight;
}
