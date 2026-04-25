package com.desss.zcms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class ContactFormRequest {
    @NotNull
    Long websiteId;
    @NotBlank
    String name;
    @NotBlank
    @Email
    String email;
    String phone;
    @NotBlank
    String message;
    /**
     * Additional dynamic fields from custom field definitions
     */
    Map<String, String> extraFields;
}
