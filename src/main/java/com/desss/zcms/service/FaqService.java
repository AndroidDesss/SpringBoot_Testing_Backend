package com.desss.zcms.service;

import com.desss.zcms.dto.FaqRequest;
import com.desss.zcms.dto.FaqResponse;
import com.desss.zcms.entity.Faq;
import com.desss.zcms.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  FAQ Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FaqService {
    private final FaqRepository repo;

    @Transactional(readOnly = true)
    public List<FaqResponse> getByPage(Long pageId) {
        return repo.findByPageIdAndIsDeletedOrderBySortOrderAsc(pageId, 0)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public FaqResponse create(FaqRequest req) {
        Faq f = Faq.builder().pageId(req.getPageId()).category(req.getCategory())
                .faqQuestion(req.getFaqQuestion()).faqAnswer(req.getFaqAnswer())
                .sortOrder(req.getSortOrder()).status(req.getStatus()).isDeleted(0).build();
        return toResponse(repo.save(f));
    }

    public FaqResponse update(Long id, FaqRequest req) {
        Faq f = repo.findById(id).orElseThrow(() -> new RuntimeException("FAQ not found"));
        f.setFaqQuestion(req.getFaqQuestion());
        f.setFaqAnswer(req.getFaqAnswer());
        f.setCategory(req.getCategory());
        f.setSortOrder(req.getSortOrder());
        f.setStatus(req.getStatus());
        return toResponse(repo.save(f));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(f -> {
            f.setIsDeleted(1);
            repo.save(f);
        });
    }

    FaqResponse toResponse(Faq f) {
        FaqResponse r = new FaqResponse();
        r.setId(f.getId());
        r.setPageId(f.getPageId());
        r.setCategory(f.getCategory());
        r.setFaqQuestion(f.getFaqQuestion());
        r.setFaqAnswer(f.getFaqAnswer());
        r.setSortOrder(f.getSortOrder());
        r.setStatus(f.getStatus());
        r.setCreatedAt(f.getCreatedAt());
        return r;
    }
}
