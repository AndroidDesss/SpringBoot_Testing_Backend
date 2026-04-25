package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "zcms_page_components",
        indexes = {
                @Index(name = "idx_pc_page",   columnList = "page_id"),
                @Index(name = "idx_pc_order",  columnList = "page_id, sort_order"),
        })
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @ToString
public class PageComponent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_id", nullable = false)
    private Long pageId;

    @Column(name = "website_id", nullable = false)
    private Long websiteId;

    @Column(name = "component_type", nullable = false, columnDefinition = "text")
    private String componentType;

    @Column(name = "component_data", columnDefinition = "longtext", nullable = false)
    private String componentData;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    // status: 1=active (default), 0=hidden
    @Column(nullable = false)
    private Integer status;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (status    == null) status    = 1;
        if (sortOrder == null) sortOrder = 0;
    }

    /** Static factory — creates one active component at a given position */
    public static PageComponent of(Long pageId, Long websiteId,
                                   String type, String data, int order) {
        PageComponent c = new PageComponent();
        c.pageId        = pageId;
        c.websiteId     = websiteId;
        c.componentType = type;
        c.componentData = data;
        c.sortOrder     = order;
        c.status        = 1;
        return c;
    }
}
