package com.desss.zcms.service;

import com.desss.zcms.dto.CustomFieldRequest;
import com.desss.zcms.dto.CustomFieldResponse;
import com.desss.zcms.entity.CustomFieldDefinition;
import com.desss.zcms.repository.CustomFieldDefinitionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// ─────────────────────────────────────────────────────────────────────────────
//  Custom Field Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomFieldService {

    private final CustomFieldDefinitionRepository repo;
    private final ObjectMapper mapper;

    public CustomFieldResponse create(CustomFieldRequest req) {
        CustomFieldDefinition entity = CustomFieldDefinition.builder()
                .websiteId(req.getWebsiteId())
                .pageId(req.getPageId())
                .fieldKey(req.getFieldKey())
                .label(req.getLabel())
                .fieldType(req.getFieldType())
                .options(req.getOptions())
                .placeholder(req.getPlaceholder())
                .isRequired(req.getIsRequired())
                .sortOrder(req.getSortOrder())
                .isDeleted(false)
                .build();
        return toResponse(repo.save(entity));
    }

    public CustomFieldResponse update(Long id, CustomFieldRequest req) {
        CustomFieldDefinition entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Custom field not found: " + id));
        entity.setLabel(req.getLabel());
        entity.setFieldType(req.getFieldType());
        entity.setOptions(req.getOptions());
        entity.setPlaceholder(req.getPlaceholder());
        entity.setIsRequired(req.getIsRequired());
        entity.setSortOrder(req.getSortOrder());
        return toResponse(repo.save(entity));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(e -> {
            e.setIsDeleted(true);
            repo.save(e);
        });
    }

    @Transactional(readOnly = true)
    public List<CustomFieldResponse> getResolved(Long websiteId, Long pageId) {
        List<CustomFieldDefinition> all = pageId != null
                ? repo.findResolvedFields(websiteId, pageId)
                : repo.findByWebsiteIdAndPageIdIsNullAndIsDeletedFalseOrderBySortOrderAsc(websiteId);

        Map<String, CustomFieldDefinition> deduped = new LinkedHashMap<>();
        for (CustomFieldDefinition f : all) {
            String key = f.getFieldKey();
            if (!deduped.containsKey(key) || f.getPageId() != null) {
                deduped.put(key, f);
            }
        }
        return deduped.values().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private CustomFieldResponse toResponse(CustomFieldDefinition e) {
        CustomFieldResponse r = new CustomFieldResponse();
        r.setId(e.getId());
        r.setWebsiteId(e.getWebsiteId());
        r.setPageId(e.getPageId());
        r.setFieldKey(e.getFieldKey());
        r.setLabel(e.getLabel());
        r.setFieldType(e.getFieldType());
        r.setPlaceholder(e.getPlaceholder());
        r.setIsRequired(e.getIsRequired());
        r.setSortOrder(e.getSortOrder());
        r.setCreatedAt(e.getCreatedAt());
        if (e.getOptions() != null) {
            try {
                r.setOptions(mapper.readValue(e.getOptions(), new TypeReference<>() {
                }));
            } catch (Exception ex) {
                r.setOptions(List.of());
            }
        }
        return r;
    }
}
