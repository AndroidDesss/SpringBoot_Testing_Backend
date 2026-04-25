package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ─────────────────────────────────────────────────────────────────────────────
//  Contact Us Form Submission
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_contact_us")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "website_id")
    private Long websiteId;
    @Column(name = "field_key", columnDefinition = "text")
    private String fieldKey;
    @Column(name = "field_value", columnDefinition = "text")
    private String fieldValue;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
    @Column(name = "created_at", length = 30)
    private String createdAt;
}
