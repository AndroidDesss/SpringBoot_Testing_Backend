package com.desss.zcms.service;

import com.desss.zcms.dto.SocialMediaRequest;
import com.desss.zcms.dto.SocialMediaResponse;
import com.desss.zcms.entity.SocialMedia;
import com.desss.zcms.repository.SocialMediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  Social Media Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SocialMediaService {
    private final SocialMediaRepository repo;

    @Transactional(readOnly = true)
    public List<SocialMediaResponse> list(Long websiteId) {
        return repo.findByWebsiteIdAndIsDeletedOrderBySortOrderAsc(websiteId, 0)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public SocialMediaResponse create(SocialMediaRequest req) {
        SocialMedia s = SocialMedia.builder()
                .websiteId(req.getWebsiteId()).mediaName(req.getMediaName()).mediaUrl(req.getMediaUrl())
                .icon(req.getIcon()).iconColor(req.getIconColor())
                .sortOrder(req.getSortOrder()).status(req.getStatus()).isDeleted(0).build();
        return toResponse(repo.save(s));
    }

    public SocialMediaResponse update(Long id, SocialMediaRequest req) {
        SocialMedia s = repo.findById(id).orElseThrow(() -> new RuntimeException("Social media not found"));
        s.setMediaName(req.getMediaName());
        s.setMediaUrl(req.getMediaUrl());
        s.setIcon(req.getIcon());
        s.setIconColor(req.getIconColor());
        s.setSortOrder(req.getSortOrder());
        s.setStatus(req.getStatus());
        return toResponse(repo.save(s));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(s -> {
            s.setIsDeleted(1);
            repo.save(s);
        });
    }

    SocialMediaResponse toResponse(SocialMedia s) {
        SocialMediaResponse r = new SocialMediaResponse();
        r.setMediaId(s.getMediaId());
        r.setWebsiteId(s.getWebsiteId());
        r.setMediaName(s.getMediaName());
        r.setMediaUrl(s.getMediaUrl());
        r.setIcon(s.getIcon());
        r.setIconColor(s.getIconColor());
        r.setSortOrder(s.getSortOrder());
        r.setStatus(s.getStatus());
        return r;
    }
}
