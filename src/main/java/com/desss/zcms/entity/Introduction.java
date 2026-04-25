package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ─────────────────────────────────────────────────────────────────────────────
//  Introduction
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_introduction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Introduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "page_id")
    private Long pageId;
    @Column(columnDefinition = "text")
    private String title;
    @Column(columnDefinition = "text")
    private String text;
    @Column(name = "title_color", length = 100)
    private String titleColor;
    @Column(name = "title_position", length = 100)
    private String titlePosition;
    @Column(name = "content_color", length = 100)
    private String contentColor;
    @Column(name = "content_position", length = 50)
    private String contentPosition;
    @Column(name = "created_at", length = 30)
    private String createdAt;
}
