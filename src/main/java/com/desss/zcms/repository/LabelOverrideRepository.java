package com.desss.zcms.repository;

import com.desss.zcms.entity.LabelOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelOverrideRepository extends JpaRepository<LabelOverride, Long> {

    /**
     * All global overrides for website
     */
    List<LabelOverride> findByWebsiteIdAndPageIdIsNull(Long websiteId);

    /**
     * Page-specific overrides
     */
    List<LabelOverride> findByWebsiteIdAndPageId(Long websiteId, Long pageId);

    /**
     * Single lookup
     */
    Optional<LabelOverride> findByWebsiteIdAndPageIdAndFieldKey(Long websiteId, Long pageId, String fieldKey);

    Optional<LabelOverride> findByWebsiteIdAndPageIdIsNullAndFieldKey(Long websiteId, String fieldKey);
}
