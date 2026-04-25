package com.desss.zcms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// ─────────────────────────────────────────────────────────────────────────────
//  CustomButton  ← NEW: configurable action buttons per page
// ─────────────────────────────────────────────────────────────────────────────
@Entity
@Table(name = "zcms_custom_buttons",
        indexes = {@Index(columnList = "website_id"), @Index(columnList = "page_id")})
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomButton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "website_id", nullable = false)
    private Long websiteId;

    @Column(name = "page_id")
    private Long pageId;

    @Column(name = "button_name", nullable = false, length = 200)
    private String buttonName;

    /**
     * navigation | form_submit | api_call | redirect | custom_js
     */
    @Column(name = "button_type", nullable = false, length = 50)
    private String buttonType = "navigation";

    /**
     * URL for navigation / redirect
     */
    @Column(name = "target_url", length = 500)
    private String targetUrl;

    /**
     * For api_call type: HTTP method GET/POST/PUT/DELETE
     */
    @Column(name = "api_method", length = 10)
    private String apiMethod;

    /**
     * For api_call: endpoint path
     */
    @Column(name = "api_endpoint", length = 500)
    private String apiEndpoint;

    /**
     * For api_call: JSON body payload template
     */
    @Column(name = "api_payload", columnDefinition = "text")
    private String apiPayload;

    /**
     * For custom_js: inline JS to execute
     */
    @Column(name = "custom_js", columnDefinition = "text")
    private String customJs;

    /**
     * primary | secondary | danger | warning | success | outline | ghost
     */
    @Column(name = "style_variant", length = 50)
    private String styleVariant = "primary";

    /**
     * Optional icon name (lucide-react icon key)
     */
    @Column(name = "icon_name", length = 100)
    private String iconName;

    /**
     * _self | _blank
     */
    @Column(name = "link_target", length = 20)
    private String linkTarget = "_self";

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
