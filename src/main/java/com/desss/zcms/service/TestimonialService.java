package com.desss.zcms.service;

import com.desss.zcms.dto.*;
import com.desss.zcms.entity.*;
import com.desss.zcms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  Testimonial Service
// ─────────────────────────────────────────────────────────────────────────────
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class TestimonialService {
    private final TestimonialRepository repo;

    @Transactional(readOnly = true)
    public List<TestimonialResponse> list(Long websiteId) {
        return repo.findByWebsiteIdAndIsDeletedOrderBySortOrderAsc(websiteId, 0)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TestimonialResponse create(TestimonialRequest req) {
        Testimonial t = Testimonial.builder()
                .websiteId(req.getWebsiteId()).image(req.getImage()).imageAlt(req.getImageAlt())
                .author(req.getAuthor()).designation(req.getDesignation()).content(req.getContent())
                .redirectUrl(req.getRedirectUrl()).sortOrder(req.getSortOrder()).status(req.getStatus()).isDeleted(0)
                .build();
        return toResponse(repo.save(t));
    }

    public TestimonialResponse update(Long id, TestimonialRequest req) {
        Testimonial t = repo.findById(id).orElseThrow(() -> new RuntimeException("Testimonial not found"));
        t.setAuthor(req.getAuthor()); t.setDesignation(req.getDesignation()); t.setContent(req.getContent());
        t.setImage(req.getImage()); t.setImageAlt(req.getImageAlt()); t.setRedirectUrl(req.getRedirectUrl());
        t.setSortOrder(req.getSortOrder()); t.setStatus(req.getStatus());
        return toResponse(repo.save(t));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(t -> { t.setIsDeleted(1); repo.save(t); });
    }

    TestimonialResponse toResponse(Testimonial t) {
        TestimonialResponse r = new TestimonialResponse();
        r.setId(t.getId()); r.setWebsiteId(t.getWebsiteId()); r.setImage(t.getImage());
        r.setImageAlt(t.getImageAlt()); r.setAuthor(t.getAuthor()); r.setDesignation(t.getDesignation());
        r.setContent(t.getContent()); r.setRedirectUrl(t.getRedirectUrl());
        r.setSortOrder(t.getSortOrder()); r.setStatus(t.getStatus()); return r;
    }
}


