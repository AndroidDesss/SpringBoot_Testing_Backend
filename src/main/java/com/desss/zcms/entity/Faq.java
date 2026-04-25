package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// ─────────────────────────────────────────────────────────────────────────────
//  FAQ
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_faq")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "page_id")
    private Long pageId;
    @Column(length = 255)
    private String category;
    @Column(name = "faq_question", columnDefinition = "text")
    private String faqQuestion;
    @Column(name = "faq_answer", columnDefinition = "text")
    private String faqAnswer;
    @Column(name = "Sort_order", length = 255)
    private String sortOrder;
    @Column
    private Integer status = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
