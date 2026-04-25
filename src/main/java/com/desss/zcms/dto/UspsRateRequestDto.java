package com.desss.zcms.dto;

import lombok.Data;

@Data
public class UspsRateRequestDto {

    private String originZip;
    private String destinationZip;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private String mailClass;
    private String processingCategory;
    private String destinationEntryFacilityType;
    private String rateIndicator;
    private String priceType;// PRIORITY / FIRST_CLASS
}
