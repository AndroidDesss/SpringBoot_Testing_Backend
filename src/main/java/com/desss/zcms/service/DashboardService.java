package com.desss.zcms.service;

import com.desss.zcms.dto.DashboardStats;
import com.desss.zcms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ─────────────────────────────────────────────────────────────────────────────
//  Dashboard Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {
    private final PageRepository pageRepo;
    private final BlogRepository blogRepo;
    private final EventRepository eventRepo;
    private final TestimonialRepository testimonialRepo;
    private final ContactUsRepository contactUsRepo;
    private final NewsletterRepository newsletterRepo;
    private final CustomFieldDefinitionRepository fieldRepo;
    private final CustomButtonRepository buttonRepo;

    @Transactional(readOnly = true)
    public DashboardStats stats(Long websiteId) {
        DashboardStats s = new DashboardStats();
        s.setTotalPages((long) pageRepo.findByWebsiteIdAndIsDeleted(websiteId, 0).size());
        s.setTotalBlogs(blogRepo.countByWebsiteIdAndIsDeleted(websiteId, 0));
        s.setTotalEvents(eventRepo.countByWebsiteIdAndIsDeleted(websiteId, 0));
        s.setTotalTestimonials((long) testimonialRepo.findByWebsiteIdAndIsDeletedOrderBySortOrderAsc(websiteId, 0).size());
        s.setTotalContactSubmissions(contactUsRepo.countByWebsiteIdAndIsDeleted(websiteId, 0));
        s.setTotalNewsletterSubscribers(newsletterRepo.countByWebsiteIdAndIsDeleted(websiteId, 0));
        s.setTotalCustomFields((long) fieldRepo.findByWebsiteIdAndPageIdIsNullAndIsDeletedFalseOrderBySortOrderAsc(websiteId).size());
        s.setTotalCustomButtons((long) buttonRepo.findByWebsiteIdAndPageIdIsNullAndIsDeletedFalseOrderBySortOrderAsc(websiteId).size());
        return s;
    }
}
