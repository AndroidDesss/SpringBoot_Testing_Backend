package com.desss.zcms.service;

import com.desss.zcms.dto.CustomButtonRequest;
import com.desss.zcms.dto.CustomButtonResponse;
import com.desss.zcms.entity.CustomButton;
import com.desss.zcms.repository.CustomButtonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  Custom Button Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomButtonService {

    private final CustomButtonRepository repo;

    public CustomButtonResponse create(CustomButtonRequest req) {
        CustomButton entity = CustomButton.builder()
                .websiteId(req.getWebsiteId())
                .pageId(req.getPageId())
                .buttonName(req.getButtonName())
                .buttonType(req.getButtonType())
                .targetUrl(req.getTargetUrl())
                .apiMethod(req.getApiMethod())
                .apiEndpoint(req.getApiEndpoint())
                .apiPayload(req.getApiPayload())
                .customJs(req.getCustomJs())
                .styleVariant(req.getStyleVariant())
                .iconName(req.getIconName())
                .linkTarget(req.getLinkTarget())
                .sortOrder(req.getSortOrder())
                .isActive(req.getIsActive())
                .isDeleted(false)
                .build();
        return toResponse(repo.save(entity));
    }

    public CustomButtonResponse update(Long id, CustomButtonRequest req) {
        CustomButton entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Button not found: " + id));
        entity.setButtonName(req.getButtonName());
        entity.setButtonType(req.getButtonType());
        entity.setTargetUrl(req.getTargetUrl());
        entity.setApiMethod(req.getApiMethod());
        entity.setApiEndpoint(req.getApiEndpoint());
        entity.setApiPayload(req.getApiPayload());
        entity.setCustomJs(req.getCustomJs());
        entity.setStyleVariant(req.getStyleVariant());
        entity.setIconName(req.getIconName());
        entity.setLinkTarget(req.getLinkTarget());
        entity.setSortOrder(req.getSortOrder());
        entity.setIsActive(req.getIsActive());
        return toResponse(repo.save(entity));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(e -> {
            e.setIsDeleted(true);
            repo.save(e);
        });
    }

    @Transactional(readOnly = true)
    public List<CustomButtonResponse> getResolved(Long websiteId, Long pageId) {
        return pageId != null
                ? repo.findResolvedButtons(websiteId, pageId).stream().map(this::toResponse).collect(Collectors.toList())
                : repo.findByWebsiteIdAndPageIdIsNullAndIsDeletedFalseOrderBySortOrderAsc(websiteId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private CustomButtonResponse toResponse(CustomButton e) {
        CustomButtonResponse r = new CustomButtonResponse();
        r.setId(e.getId());
        r.setWebsiteId(e.getWebsiteId());
        r.setPageId(e.getPageId());
        r.setButtonName(e.getButtonName());
        r.setButtonType(e.getButtonType());
        r.setTargetUrl(e.getTargetUrl());
        r.setApiMethod(e.getApiMethod());
        r.setApiEndpoint(e.getApiEndpoint());
        r.setApiPayload(e.getApiPayload());
        r.setCustomJs(e.getCustomJs());
        r.setStyleVariant(e.getStyleVariant());
        r.setIconName(e.getIconName());
        r.setLinkTarget(e.getLinkTarget());
        r.setSortOrder(e.getSortOrder());
        r.setIsActive(e.getIsActive());
        r.setCreatedAt(e.getCreatedAt());
        return r;
    }
}
