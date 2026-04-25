package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Mail Config ───────────────────────────────────────────────────────────────
@Data
public class MailConfigRequest {
    @NotNull
    Long websiteId;
    @NotBlank
    String host;
    @NotBlank
    String port;
    @NotBlank
    String email;
    String password;
    String mailFrom;
    Integer status = 1;
}
