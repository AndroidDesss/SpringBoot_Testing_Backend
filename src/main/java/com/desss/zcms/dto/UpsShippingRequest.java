package com.desss.zcms.dto;

import lombok.Data;

@Data
public class UpsShippingRequest {
    private String shippingZip;
    private String warehouseZipCode;
    private Double weight;
}
