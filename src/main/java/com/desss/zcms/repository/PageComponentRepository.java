package com.desss.zcms.repository;


import com.desss.zcms.entity.PageComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageComponentRepository extends JpaRepository<PageComponent, Long> {

    /** Load active components for a page in sort order */
    List<PageComponent> findByPageIdAndStatusOrderBySortOrderAsc(Long pageId, Integer status);

    /**
     * Hard DELETE all rows for a page.
     * Used in bulk-save: wipe old → insert fresh.
     * Native SQL = simplest, fastest, no Hibernate cache issues.
     */
    @Modifying
    @Query(value = "DELETE FROM zcms_page_components WHERE page_id = :pageId", nativeQuery = true)
    void deleteAllByPageId(@Param("pageId") Long pageId);

    long countByWebsiteIdAndStatus(Long websiteId, Integer status);
}


