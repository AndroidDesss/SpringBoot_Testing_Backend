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
//  Our Service
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_our_service")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OurService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "page_id")
    private Long pageId;
    @Column(columnDefinition = "text")
    private String image;
    @Column(name = "image_alt", length = 255)
    private String imageAlt;
    @Column(name = "image_title", length = 255)
    private String imageTitle;
    @Column(length = 255)
    private String title;
    @Column(name = "title_color", length = 30)
    private String titleColor;
    @Column(name = "redirect_url", length = 255)
    private String redirectUrl;
    @Column(name = "open_new_tab")
    private Integer openNewTab = 0;
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
