package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ─────────────────────────────────────────────────────────────────────────────
//  Social Media
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_social_media")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long mediaId;
    @Column(name = "website_id")
    private Long websiteId;
    @Column(name = "media_name", length = 255)
    private String mediaName;
    @Column(name = "media_url", length = 255)
    private String mediaUrl;
    @Column(length = 255)
    private String icon;
    @Column(name = "icon_color", length = 255)
    private String iconColor;
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
    @Column
    private Integer status = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
}
