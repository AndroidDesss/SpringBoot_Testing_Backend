package com.desss.zcms.repository;

import com.desss.zcms.entity.CustomFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomFieldDefinitionRepository extends JpaRepository<CustomFieldDefinition, Long> {

    /**
     * Global fields for website (no page override)
     */
    List<CustomFieldDefinition> findByWebsiteIdAndPageIdIsNullAndIsDeletedFalseOrderBySortOrderAsc(Long websiteId);

    /**
     * Page-specific field overrides
     */
    List<CustomFieldDefinition> findByWebsiteIdAndPageIdAndIsDeletedFalseOrderBySortOrderAsc(Long websiteId, Long pageId);

    /**
     * Resolve: page-level first, then fall back to global
     */
    @Query("""
            SELECT c FROM CustomFieldDefinition c
            WHERE c.websiteId = :websiteId
              AND (c.pageId = :pageId OR c.pageId IS NULL)
              AND c.isDeleted = false
            ORDER BY c.pageId DESC NULLS LAST, c.sortOrder ASC
            """)
    List<CustomFieldDefinition> findResolvedFields(Long websiteId, Long pageId);
}
