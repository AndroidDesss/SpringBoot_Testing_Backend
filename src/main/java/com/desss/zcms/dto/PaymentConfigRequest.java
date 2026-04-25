package com.desss.zcms.dto;

import lombok.Data;

@Data
public class PaymentConfigRequest {
    Long websiteId;
    String providerName;
    String apiLoginId;
    String transactionKey;
    String environment;
    Integer status = 1;
}
