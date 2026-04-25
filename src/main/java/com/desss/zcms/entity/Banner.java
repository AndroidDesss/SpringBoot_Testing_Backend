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
//  Banner
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_banner")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "page_id")
    private Long pageId;
    @Column(columnDefinition = "text")
    private String title;
    @Column(name = "title_color", length = 150)
    private String titleColor;
    @Column(columnDefinition = "text")
    private String text;
    @Column(name = "text_color", length = 100)
    private String textColor;
    @Column(columnDefinition = "text")
    private String image;
    @Column(name = "mobile_image", columnDefinition = "text")
    private String mobileImage;
    @Column(name = "image_alt", length = 255)
    private String imageAlt;
    @Column(name = "image_title", length = 255)
    private String imageTitle;
    @Column(name = "readmore_btn")
    private Integer readmoreBtn = 0;
    @Column(name = "readmore_label", length = 100)
    private String readmoreLabel;
    @Column(name = "readmore_url", length = 255)
    private String readmoreUrl;
    @Column(name = "open_new_tab")
    private Integer openNewTab = 0;
    @Column(name = "button_type", length = 250)
    private String buttonType;
    @Column(name = "button_position", length = 50)
    private String buttonPosition;
    @Column(name = "btn_background_color", length = 100)
    private String btnBackgroundColor;
    @Column(name = "label_color", length = 100)
    private String labelColor;
    @Column(name = "text_position", length = 100)
    private String textPosition;
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
