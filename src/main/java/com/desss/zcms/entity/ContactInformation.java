package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ─────────────────────────────────────────────────────────────────────────────
//  Contact Information
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_contact_information")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "website_id")
    private Long websiteId;
    @Column(length = 255)
    private String title;
    @Column(name = "phone_no", length = 255)
    private String phoneNo;
    @Column(length = 255)
    private String email;
    @Column(columnDefinition = "text")
    private String address;
    @Column(length = 255)
    private String fax;
    @Column(name = "working_hours", columnDefinition = "text")
    private String workingHours;
    @Column
    private Integer status = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
}
