package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "zcms_payment_configuration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long websiteId;

    private String providerName; // AUTHORIZE_NET

    private String apiLoginId;

    @Column(length = 500)
    private String transactionKey; // encrypted

    private String environment; // SANDBOX / PRODUCTION

    private Integer status = 1;
}
