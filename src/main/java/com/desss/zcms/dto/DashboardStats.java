package com.desss.zcms.dto;

import lombok.Data;

// ── Dashboard Stats ───────────────────────────────────────────────────────────
@Data
public class DashboardStats {
    Long totalPages;
    Long totalBlogs;
    Long totalEvents;
    Long totalTestimonials;
    Long totalContactSubmissions;
    Long totalNewsletterSubscribers;
    Long totalCustomFields;
    Long totalCustomButtons;
}
