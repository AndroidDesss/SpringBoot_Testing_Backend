package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Mail Configuration
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_mail_configuration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "website_id")
    private Long websiteId;
    @Column(length = 255)
    private String host;
    @Column(length = 255)
    private String port;
    @Column(length = 255)
    private String email;
    /**
     * Stored encrypted in DB via Jasypt.
     * Use ENC(encryptedValue) format in application properties,
     * or encrypt manually before saving via EncryptionService.
     * Column is intentionally larger to hold the encrypted ciphertext.
     */
    @Column(length = 512)
    private String password;
    @Column(name = "mail_from", length = 255)
    private String mailFrom;
    @Column
    private Integer status = 0;
}
