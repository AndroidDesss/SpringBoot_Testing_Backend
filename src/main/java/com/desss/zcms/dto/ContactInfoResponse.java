package com.desss.zcms.dto;

import lombok.Data;

@Data
public class ContactInfoResponse {
    Long id;
    Long websiteId;
    String title;
    String phoneNo;
    String email;
    String address;
    String fax;
    String workingHours;
    Integer status;
}
