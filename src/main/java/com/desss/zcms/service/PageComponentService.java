package com.desss.zcms.service;

import com.desss.zcms.dto.PageComponentBulkRequest;
import com.desss.zcms.dto.PageComponentRequest;
import com.desss.zcms.dto.PageComponentResponse;
import com.desss.zcms.entity.PageComponent;
import com.desss.zcms.repository.PageComponentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageComponentService {

    private final PageComponentRepository repo;

    // ── List ─────────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<PageComponentResponse> listByPage(Long pageId) {
        return repo.findByPageIdAndStatusOrderBySortOrderAsc(pageId, 1)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Delete one ────────────────────────────────────────────────────────────
    /** Hard delete a single component by id */
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
        log.info("Deleted component id={}", id);
    }

    // ── Bulk save ─────────────────────────────────────────────────────────────
    /**
     * BULK SAVE — clean slate every time.
     *
     * 1. Hard DELETE all existing rows for this page  (native SQL — instant)
     * 2. INSERT new rows in canvas order              (saveAll — one batch)
     *
     * Result: table always has exactly N rows per page. Zero junk rows.
     * Order is stored in sort_order (0-based index from the canvas array).
     */
    @Transactional
    public List<PageComponentResponse> bulkSave(PageComponentBulkRequest req) {
        Long pageId    = req.getPageId();
        Long websiteId = req.getWebsiteId();

        log.info("bulkSave — pageId={} websiteId={} components={}",
                pageId, websiteId,
                req.getComponents() == null ? 0 : req.getComponents().size());

        // Step 1 — hard delete all existing rows for this page
        repo.deleteAllByPageId(pageId);
        log.info("Deleted all existing rows for pageId={}", pageId);

        // Step 2 — build and insert new rows
        if (req.getComponents() == null || req.getComponents().isEmpty()) {
            log.info("No components to insert — page is now empty");
            return new ArrayList<>();
        }

        List<PageComponent> toInsert = new ArrayList<>();
        for (int i = 0; i < req.getComponents().size(); i++) {
            PageComponentRequest item = req.getComponents().get(i);

            String type = item.getComponentType();
            String data = item.getComponentData();

            if (type == null || type.isBlank()) {
                log.warn("Skipping item[{}] — componentType is blank", i);
                continue;
            }
            if (data == null || data.isBlank()) {
                log.warn("Skipping item[{}] — componentData is blank", i);
                continue;
            }

            // sort_order: use value from request if provided, else use array index
            int order = (item.getSortOrder() != null) ? item.getSortOrder() : i;
            toInsert.add(PageComponent.of(pageId, websiteId, type, data, order));
        }

        List<PageComponent> saved = repo.saveAll(toInsert);
        log.info("Inserted {} components for pageId={}", saved.size(), pageId);
        return saved.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Mapper ────────────────────────────────────────────────────────────────
    private PageComponentResponse toResponse(PageComponent c) {
        PageComponentResponse r = new PageComponentResponse();
        r.setId(c.getId());
        r.setPageId(c.getPageId());
        r.setWebsiteId(c.getWebsiteId());
        r.setComponentType(c.getComponentType());
        r.setComponentData(c.getComponentData());
        r.setSortOrder(c.getSortOrder());
        r.setStatus(c.getStatus());
        r.setCreatedAt(c.getCreatedAt());
        r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }
}


