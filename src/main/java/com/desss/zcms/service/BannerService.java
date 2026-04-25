package com.desss.zcms.service;

import com.desss.zcms.dto.BannerRequest;
import com.desss.zcms.dto.BannerResponse;
import com.desss.zcms.entity.Banner;
import com.desss.zcms.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  Banner Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BannerService {
    private final BannerRepository repo;

    public List<BannerResponse> getByPage(Long pageId) {
        return repo.findByPageIdAndIsDeletedOrderBySortOrderAsc(pageId, 0)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public BannerResponse create(BannerRequest req) {
        return toResponse(repo.save(fromRequest(req, new Banner())));
    }

    public BannerResponse update(Long id, BannerRequest req) {
        Banner b = repo.findById(id).orElseThrow(() -> new RuntimeException("Banner not found: " + id));
        return toResponse(repo.save(fromRequest(req, b)));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(b -> {
            b.setIsDeleted(1);
            repo.save(b);
        });
    }

    private Banner fromRequest(BannerRequest req, Banner b) {
        b.setPageId(req.getPageId());
        b.setTitle(req.getTitle());
        b.setTitleColor(req.getTitleColor());
        b.setText(req.getText());
        b.setTextColor(req.getTextColor());
        b.setImage(req.getImage());
        b.setMobileImage(req.getMobileImage());
        b.setImageAlt(req.getImageAlt());
        b.setImageTitle(req.getImageTitle());
        b.setReadmoreBtn(req.getReadmoreBtn());
        b.setReadmoreLabel(req.getReadmoreLabel());
        b.setReadmoreUrl(req.getReadmoreUrl());
        b.setOpenNewTab(req.getOpenNewTab());
        b.setButtonType(req.getButtonType());
        b.setButtonPosition(req.getButtonPosition());
        b.setBtnBackgroundColor(req.getBtnBackgroundColor());
        b.setLabelColor(req.getLabelColor());
        b.setTextPosition(req.getTextPosition());
        b.setSortOrder(req.getSortOrder());
        b.setStatus(req.getStatus());
        return b;
    }

    private BannerResponse toResponse(Banner b) {
        BannerResponse r = new BannerResponse();
        r.setId(b.getId());
        r.setPageId(b.getPageId());
        r.setTitle(b.getTitle());
        r.setTitleColor(b.getTitleColor());
        r.setText(b.getText());
        r.setTextColor(b.getTextColor());
        r.setImage(b.getImage());
        r.setMobileImage(b.getMobileImage());
        r.setImageAlt(b.getImageAlt());
        r.setImageTitle(b.getImageTitle());
        r.setReadmoreBtn(b.getReadmoreBtn());
        r.setReadmoreLabel(b.getReadmoreLabel());
        r.setReadmoreUrl(b.getReadmoreUrl());
        r.setOpenNewTab(b.getOpenNewTab());
        r.setButtonType(b.getButtonType());
        r.setButtonPosition(b.getButtonPosition());
        r.setBtnBackgroundColor(b.getBtnBackgroundColor());
        r.setLabelColor(b.getLabelColor());
        r.setTextPosition(b.getTextPosition());
        r.setSortOrder(b.getSortOrder());
        r.setStatus(b.getStatus());
        return r;
    }
}
