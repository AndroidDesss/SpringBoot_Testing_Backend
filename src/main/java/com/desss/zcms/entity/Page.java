package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// ─────────────────────────────────────────────────────────────────────────────
//  Page
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_pages")
@EntityListeners(AuditingEntityListener.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Page {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "website_id", nullable = false)
    private Long websiteId;

    @Column(length = 150)
    private String title;

    @Column(length = 255)
    private String url;

    @Column(name = "page_extension", length = 30)
    private String pageExtension = ".html";

    @Column(name = "page_components", columnDefinition = "longtext")
    private String pageComponents;

    @Column(name = "external_page", nullable = false)
    private Integer externalPage = 0;

    @Column(nullable = false)
    private Integer status = 1;

    @Column(name = "home_status")
    private Integer homeStatus = 0;

    @Column(nullable = false)
    private Integer publish = 0;

    @Column(name = "is_sitemap")
    private Integer isSitemap = 0;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted = 0;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}


