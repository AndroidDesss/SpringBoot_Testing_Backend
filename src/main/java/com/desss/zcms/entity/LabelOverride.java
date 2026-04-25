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
//  LabelOverride  ← stores customer-customized label names
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_label_overrides",
        uniqueConstraints = @UniqueConstraint(columnNames = {"website_id", "page_id", "field_key"}))
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "website_id", nullable = false)
    private Long websiteId;

    /**
     * null = global override for website; non-null = page-specific
     */
    @Column(name = "page_id")
    private Long pageId;

    /**
     * e.g. "banner.title", "blog.author_name"
     */
    @Column(name = "field_key", nullable = false, length = 200)
    private String fieldKey;

    /**
     * The custom label text the customer wants
     */
    @Column(name = "custom_label", nullable = false, length = 300)
    private String customLabel;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
