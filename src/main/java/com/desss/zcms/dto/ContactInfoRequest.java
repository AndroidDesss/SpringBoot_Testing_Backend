package com.desss.zcms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Contact Information ────────────────────────────────────────────────────────
@Data
public class ContactInfoRequest {
    @NotNull
    Long websiteId;
    String title;
    String phoneNo;
    String email;
    String address;
    String fax;
    String workingHours;
    Integer status = 1;
}
