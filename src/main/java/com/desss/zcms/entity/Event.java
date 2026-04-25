package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// ─────────────────────────────────────────────────────────────────────────────
//  Event
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_event")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "website_id")
    private Long websiteId;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(columnDefinition = "text")
    private String title;
    @Column(columnDefinition = "text")
    private String image;
    @Column(name = "image_alt", length = 255)
    private String imageAlt;
    @Column(name = "short_description", columnDefinition = "text")
    private String shortDescription;
    @Column(columnDefinition = "text")
    private String description;
    @Column(length = 255)
    private String url;
    @Column(name = "event_date", length = 50)
    private String eventDate;
    @Column(name = "meta_title", length = 255)
    private String metaTitle;
    @Column(name = "meta_description", columnDefinition = "text")
    private String metaDescription;
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
    @Column
    private Integer status = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
