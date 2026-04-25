package com.desss.zcms.dto;

import lombok.Data;

@Data
public class MailConfigResponse {
    Long id;
    Long websiteId;
    String host;
    String port;
    String email;
    String mailFrom;
    Integer status;
}
