package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

// ─────────────────────────────────────────────────────────────────────────────
// Website entity  (maps to zcms_websites)
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_websites")
@EntityListeners(AuditingEntityListener.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Website {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 300)
    private String domain;

    @Column(length = 100)
    private String timezone;

    @Column(nullable = false)
    private Integer status = 1;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted = 0;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
