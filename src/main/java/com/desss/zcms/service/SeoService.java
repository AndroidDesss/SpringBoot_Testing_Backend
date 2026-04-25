package com.desss.zcms.service;

import com.desss.zcms.dto.SeoRequest;
import com.desss.zcms.dto.SeoResponse;
import com.desss.zcms.entity.Seo;
import com.desss.zcms.repository.SeoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// ─────────────────────────────────────────────────────────────────────────────
//  SEO Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SeoService {
    private final SeoRepository repo;

    @Transactional(readOnly = true)
    public Optional<SeoResponse> getByPage(Long pageId) {
        return repo.findByPageId(pageId).map(this::toResponse);
    }

    public SeoResponse upsert(SeoRequest req) {
        Seo seo = repo.findByPageId(req.getPageId()).orElse(Seo.builder().pageId(req.getPageId()).build());
        seo.setH1Tag(req.getH1Tag());
        seo.setH2Tag(req.getH2Tag());
        seo.setMetaTitle(req.getMetaTitle());
        seo.setMetaDescription(req.getMetaDescription());
        seo.setMetaKeyword(req.getMetaKeyword());
        seo.setTargetKeyword(req.getTargetKeyword());
        seo.setMetaMisc(req.getMetaMisc());
        return toResponse(repo.save(seo));
    }

    SeoResponse toResponse(Seo s) {
        SeoResponse r = new SeoResponse();
        r.setId(s.getId());
        r.setPageId(s.getPageId());
        r.setH1Tag(s.getH1Tag());
        r.setH2Tag(s.getH2Tag());
        r.setMetaTitle(s.getMetaTitle());
        r.setMetaDescription(s.getMetaDescription());
        r.setMetaKeyword(s.getMetaKeyword());
        r.setTargetKeyword(s.getTargetKeyword());
        r.setMetaMisc(s.getMetaMisc());
        return r;
    }
}
