package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ─────────────────────────────────────────────────────────────────────────────
//  SEO
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_seo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "page_id")
    private Long pageId;
    @Column(name = "h1_tag", length = 100)
    private String h1Tag;
    @Column(name = "h2_tag", length = 100)
    private String h2Tag;
    @Column(name = "meta_title", length = 100)
    private String metaTitle;
    @Column(name = "meta_description", length = 250)
    private String metaDescription;
    @Column(name = "meta_keyword", length = 255)
    private String metaKeyword;
    @Column(name = "target_keyword", length = 255)
    private String targetKeyword;
    @Column(name = "meta_misc", columnDefinition = "text")
    private String metaMisc;
    @Column(name = "created_at", length = 30)
    private String createdAt;
}
