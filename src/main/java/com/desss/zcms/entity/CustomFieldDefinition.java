package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// ─────────────────────────────────────────────────────────────────────────────
//  CustomFieldDefinition  ← NEW: per-website or per-page custom field schemas
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_custom_field_definitions",
        indexes = {@Index(columnList = "website_id"), @Index(columnList = "page_id")})
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * null = global for this website; non-null = page-level override
     */
    @Column(name = "website_id", nullable = false)
    private Long websiteId;

    /**
     * null means global default, non-null means page-level override
     */
    @Column(name = "page_id")
    private Long pageId;

    /**
     * system key that uniquely identifies a backend field, e.g. "banner.title"
     */
    @Column(name = "field_key", nullable = false, length = 200)
    private String fieldKey;

    /**
     * label shown to the admin user
     */
    @Column(name = "label", nullable = false, length = 200)
    private String label;

    /**
     * text | textarea | number | select | checkbox | date | image | file
     */
    @Column(name = "field_type", nullable = false, length = 50)
    private String fieldType = "text";

    /**
     * JSON array of options for select type: ["opt1","opt2"]
     */
    @Column(name = "options", columnDefinition = "text")
    private String options;

    @Column(name = "placeholder", length = 300)
    private String placeholder;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = false;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
