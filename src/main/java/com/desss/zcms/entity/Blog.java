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
//  Blog
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_blog")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
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
    @Column(name = "image_title", length = 255)
    private String imageTitle;
    @Column(name = "short_description", columnDefinition = "text")
    private String shortDescription;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "meta_title", length = 255)
    private String metaTitle;
    @Column(name = "meta_description", columnDefinition = "text")
    private String metaDescription;
    @Column(name = "meta_keyword", length = 255)
    private String metaKeyword;
    @Column(name = "h1_tag", length = 100)
    private String h1Tag;
    @Column(name = "h2_tag", length = 100)
    private String h2Tag;
    @Column(length = 255)
    private String url;
    @Column(name = "page_extension", length = 30)
    private String pageExtension;
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
    @Column
    private Integer status = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
    @Column(name = "created_at")
    private String createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
