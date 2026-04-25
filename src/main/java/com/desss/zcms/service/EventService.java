package com.desss.zcms.service;

import com.desss.zcms.dto.EventRequest;
import com.desss.zcms.dto.EventResponse;
import com.desss.zcms.dto.PaginatedResponse;
import com.desss.zcms.entity.Event;
import com.desss.zcms.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// ─────────────────────────────────────────────────────────────────────────────
//  Event Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {
    private final EventRepository repo;

    @Transactional(readOnly = true)
    public PaginatedResponse<EventResponse> list(Long websiteId, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("sortOrder").ascending());
        return PaginatedResponse.of(
                repo.findByWebsiteIdAndIsDeleted(websiteId, 0, pageable).map(this::toResponse)
        );
    }

    public EventResponse create(EventRequest req) {
        Event e = new Event();
        return toResponse(repo.save(apply(req, e)));
    }

    public EventResponse update(Long id, EventRequest req) {
        Event e = repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found: " + id));
        return toResponse(repo.save(apply(req, e)));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(e -> {
            e.setIsDeleted(1);
            repo.save(e);
        });
    }

    private Event apply(EventRequest req, Event e) {
        e.setWebsiteId(req.getWebsiteId());
        e.setCategoryId(req.getCategoryId());
        e.setTitle(req.getTitle());
        e.setImage(req.getImage());
        e.setImageAlt(req.getImageAlt());
        e.setShortDescription(req.getShortDescription());
        e.setDescription(req.getDescription());
        e.setUrl(req.getUrl());
        e.setEventDate(req.getEventDate());
        e.setMetaTitle(req.getMetaTitle());
        e.setMetaDescription(req.getMetaDescription());
        e.setSortOrder(req.getSortOrder());
        e.setStatus(req.getStatus());
        e.setIsDeleted(0);
        return e;
    }

    EventResponse toResponse(Event e) {
        EventResponse r = new EventResponse();
        r.setId(e.getId());
        r.setWebsiteId(e.getWebsiteId());
        r.setCategoryId(e.getCategoryId());
        r.setTitle(e.getTitle());
        r.setImage(e.getImage());
        r.setImageAlt(e.getImageAlt());
        r.setShortDescription(e.getShortDescription());
        r.setDescription(e.getDescription());
        r.setUrl(e.getUrl());
        r.setEventDate(e.getEventDate());
        r.setMetaTitle(e.getMetaTitle());
        r.setMetaDescription(e.getMetaDescription());
        r.setSortOrder(e.getSortOrder());
        r.setStatus(e.getStatus());
        return r;
    }
}