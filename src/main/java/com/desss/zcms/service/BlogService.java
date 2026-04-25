package com.desss.zcms.service;

import com.desss.zcms.dto.*;
import com.desss.zcms.entity.Blog;
import com.desss.zcms.entity.BlogCategory;
import com.desss.zcms.repository.BlogCategoryRepository;
import com.desss.zcms.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  Blog Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BlogService {
    private final BlogRepository blogRepo;
    private final BlogCategoryRepository catRepo;

    @Transactional(readOnly = true)
    public PaginatedResponse<BlogResponse> list(Long websiteId, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("sortOrder").ascending());
        return PaginatedResponse.of(
                blogRepo.findByWebsiteIdAndIsDeleted(websiteId, 0, pageable).map(this::toResponse)
        );
    }

    @Transactional(readOnly = true)
    public BlogResponse getByUrl(Long websiteId, String url) {
        return blogRepo.findByWebsiteIdAndUrlAndIsDeleted(websiteId, url, 0)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Blog not found: " + url));
    }

    public BlogResponse create(BlogRequest req) {
        Blog b = new Blog();
        return toResponse(blogRepo.save(applyRequest(req, b)));
    }

    public BlogResponse update(Long id, BlogRequest req) {
        Blog b = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("Blog not found: " + id));
        return toResponse(blogRepo.save(applyRequest(req, b)));
    }

    public void delete(Long id) {
        blogRepo.findById(id).ifPresent(b -> {
            b.setIsDeleted(1);
            blogRepo.save(b);
        });
    }

    @Transactional(readOnly = true)
    public List<BlogCategoryResponse> listCategories(Long websiteId) {
        return catRepo.findByWebsiteIdAndIsDeleted(websiteId, 0)
                .stream().map(c -> {
                    BlogCategoryResponse r = new BlogCategoryResponse();
                    r.setId(c.getId());
                    r.setWebsiteId(c.getWebsiteId());
                    r.setCategoryName(c.getCategoryName());
                    r.setUrl(c.getUrl());
                    r.setStatus(c.getStatus());
                    r.setBlogCount(blogRepo.findByWebsiteIdAndCategoryIdAndIsDeletedOrderBySortOrderAsc(
                            websiteId, c.getId(), 0).size() * 1L);
                    return r;
                }).collect(Collectors.toList());
    }

    public BlogCategoryResponse createCategory(BlogCategoryRequest req) {
        BlogCategory c = BlogCategory.builder()
                .websiteId(req.getWebsiteId())
                .categoryName(req.getCategoryName())
                .url(req.getUrl())
                .status(req.getStatus())
                .isDeleted(0)
                .build();
        BlogCategory saved = catRepo.save(c);
        BlogCategoryResponse r = new BlogCategoryResponse();
        r.setId(saved.getId());
        r.setWebsiteId(saved.getWebsiteId());
        r.setCategoryName(saved.getCategoryName());
        r.setUrl(saved.getUrl());
        r.setBlogCount(0L);
        return r;
    }

    private Blog applyRequest(BlogRequest req, Blog b) {
        b.setWebsiteId(req.getWebsiteId());
        b.setCategoryId(req.getCategoryId());
        b.setTitle(req.getTitle());
        b.setImage(req.getImage());
        b.setImageAlt(req.getImageAlt());
        b.setImageTitle(req.getImageTitle());
        b.setShortDescription(req.getShortDescription());
        b.setDescription(req.getDescription());
        b.setUrl(req.getUrl());
        b.setPageExtension(req.getPageExtension());
        b.setMetaTitle(req.getMetaTitle());
        b.setMetaDescription(req.getMetaDescription());
        b.setMetaKeyword(req.getMetaKeyword());
        b.setH1Tag(req.getH1Tag());
        b.setH2Tag(req.getH2Tag());
        b.setSortOrder(req.getSortOrder());
        b.setStatus(req.getStatus());
        b.setIsDeleted(0);
        return b;
    }

    BlogResponse toResponse(Blog b) {
        BlogResponse r = new BlogResponse();
        r.setId(b.getId());
        r.setWebsiteId(b.getWebsiteId());
        r.setCategoryId(b.getCategoryId());
        r.setTitle(b.getTitle());
        r.setImage(b.getImage());
        r.setImageAlt(b.getImageAlt());
        r.setShortDescription(b.getShortDescription());
        r.setDescription(b.getDescription());
        r.setUrl(b.getUrl());
        r.setMetaTitle(b.getMetaTitle());
        r.setMetaDescription(b.getMetaDescription());
        r.setH1Tag(b.getH1Tag());
        r.setH2Tag(b.getH2Tag());
        r.setSortOrder(b.getSortOrder());
        r.setStatus(b.getStatus());
        r.setCreatedAt(b.getCreatedAt());
        if (b.getCategoryId() != null) {
            catRepo.findById(b.getCategoryId())
                    .ifPresent(c -> r.setCategoryName(c.getCategoryName()));
        }
        return r;
    }
}
