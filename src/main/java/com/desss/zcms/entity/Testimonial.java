package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

// ─────────────────────────────────────────────────────────────────────────────
//  Testimonial
// ─────────────────────────────────────────────────────────────────────────────
@Entity @Table(name = "zcms_testimonial")
@EntityListeners(AuditingEntityListener.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Testimonial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "website_id") private Long websiteId;
    @Column(columnDefinition = "text") private String image;
    @Column(name = "image_alt", length = 255) private String imageAlt;
    @Column(columnDefinition = "text") private String author;
    @Column(columnDefinition = "text") private String designation;
    @Column(columnDefinition = "text") private String content;
    @Column(name = "redirect_url", length = 255) private String redirectUrl;
    @Column(name = "sort_order") private Integer sortOrder = 0;
    @Column private Integer status = 0;
    @Column(name = "is_deleted") private Integer isDeleted = 0;
    @LastModifiedDate @Column(name = "updated_at") private LocalDateTime updatedAt;
}


