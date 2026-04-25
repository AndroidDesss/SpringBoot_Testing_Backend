package com.desss.zcms.service;

import com.desss.zcms.dto.*;
import com.desss.zcms.entity.Page;
import com.desss.zcms.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PageService {

    private final PageRepository repo;

    @Transactional(readOnly = true)
    public List<PageDto> listByWebsite(Long websiteId) {
        return repo.findByWebsiteIdAndIsDeleted(websiteId, 0)
            .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageDto getByUrl(Long websiteId, String url) {
        return repo.findByWebsiteIdAndUrlAndIsDeleted(websiteId, url, 0)
            .map(this::toDto)
            .orElseThrow(() -> new RuntimeException("Page not found: " + url));
    }

    @Transactional(readOnly = true)
    public PageDto getById(Long id) {
        return repo.findById(id)
            .map(this::toDto)
            .orElseThrow(() -> new RuntimeException("Page not found: " + id));
    }

    public PageDto create(PageDto req) {
        Page p = new Page();
        applyDto(req, p);
        p.setIsDeleted(0);
        return toDto(repo.save(p));
    }

    public PageDto update(Long id, PageDto req) {
        Page p = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Page not found: " + id));
        applyDto(req, p);
        return toDto(repo.save(p));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(p -> {
            p.setIsDeleted(1);
            repo.save(p);
        });
    }

    public PageDto publish(Long id, boolean publish) {
        Page p = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Page not found: " + id));
        p.setPublish(publish ? 1 : 0);
        return toDto(repo.save(p));
    }

    private void applyDto(PageDto dto, Page p) {
        p.setWebsiteId(dto.getWebsiteId());
        p.setTitle(dto.getTitle());
        p.setUrl(dto.getUrl());
        p.setPageExtension(dto.getPageExtension() != null ? dto.getPageExtension() : ".html");
        p.setPageComponents(dto.getPageComponents());
        p.setExternalPage(dto.getExternalPage() != null ? dto.getExternalPage() : 0);
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        p.setHomeStatus(dto.getHomeStatus() != null ? dto.getHomeStatus() : 0);
        p.setIsSitemap(dto.getIsSitemap() != null ? dto.getIsSitemap() : 0);
    }

    PageDto toDto(Page p) {
        PageDto d = new PageDto();
        d.setId(p.getId());
        d.setWebsiteId(p.getWebsiteId());
        d.setTitle(p.getTitle());
        d.setUrl(p.getUrl());
        d.setPageExtension(p.getPageExtension());
        d.setPageComponents(p.getPageComponents());
        d.setExternalPage(p.getExternalPage());
        d.setStatus(p.getStatus());
        d.setHomeStatus(p.getHomeStatus());
        d.setPublish(p.getPublish());
        d.setIsSitemap(p.getIsSitemap());
        d.setCreatedAt(p.getCreatedAt());
        d.setUpdatedAt(p.getUpdatedAt());
        return d;
    }
}
