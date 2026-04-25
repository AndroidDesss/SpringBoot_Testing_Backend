package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ─────────────────────────────────────────────────────────────────────────────
//  Blog Category
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_blog_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "website_id")
    private Long websiteId;
    @Column(name = "category_name", length = 255)
    private String categoryName;
    @Column(length = 255)
    private String url;
    @Column
    private Integer status = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
    @Column(name = "created_at")
    private String createdAt;
}
