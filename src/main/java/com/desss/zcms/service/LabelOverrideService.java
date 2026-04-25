package com.desss.zcms.service;

import com.desss.zcms.dto.*;
import com.desss.zcms.entity.*;
import com.desss.zcms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Label Override Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LabelOverrideService {

    private final LabelOverrideRepository repo;

    public LabelOverrideResponse upsert(LabelOverrideRequest req) {
        Optional<LabelOverride> existing = req.getPageId() != null
                ? repo.findByWebsiteIdAndPageIdAndFieldKey(req.getWebsiteId(), req.getPageId(), req.getFieldKey())
                : repo.findByWebsiteIdAndPageIdIsNullAndFieldKey(req.getWebsiteId(), req.getFieldKey());

        LabelOverride entity = existing.orElse(LabelOverride.builder()
                .websiteId(req.getWebsiteId())
                .pageId(req.getPageId())
                .fieldKey(req.getFieldKey())
                .build());
        entity.setCustomLabel(req.getCustomLabel());
        return toResponse(repo.save(entity));
    }

    public void delete(Long id) { repo.deleteById(id); }

    @Transactional(readOnly = true)
    public Map<String, String> resolvedLabelMap(Long websiteId, Long pageId) {
        Map<String, String> map = new LinkedHashMap<>();
        repo.findByWebsiteIdAndPageIdIsNull(websiteId)
                .forEach(o -> map.put(o.getFieldKey(), o.getCustomLabel()));
        if (pageId != null) {
            repo.findByWebsiteIdAndPageId(websiteId, pageId)
                    .forEach(o -> map.put(o.getFieldKey(), o.getCustomLabel()));
        }
        return map;
    }

    private LabelOverrideResponse toResponse(LabelOverride e) {
        LabelOverrideResponse r = new LabelOverrideResponse();
        r.setId(e.getId()); r.setWebsiteId(e.getWebsiteId()); r.setPageId(e.getPageId());
        r.setFieldKey(e.getFieldKey()); r.setCustomLabel(e.getCustomLabel());
        r.setCreatedAt(e.getCreatedAt());
        return r;
    }
}

