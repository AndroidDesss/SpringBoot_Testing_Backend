package com.desss.zcms.repository;

import com.desss.zcms.entity.CustomButton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomButtonRepository extends JpaRepository<CustomButton, Long> {

    List<CustomButton> findByWebsiteIdAndPageIdIsNullAndIsDeletedFalseOrderBySortOrderAsc(Long websiteId);

    List<CustomButton> findByWebsiteIdAndPageIdAndIsDeletedFalseOrderBySortOrderAsc(Long websiteId, Long pageId);

    @Query("""
            SELECT b FROM CustomButton b
            WHERE b.websiteId = :websiteId
              AND (b.pageId = :pageId OR b.pageId IS NULL)
              AND b.isDeleted = false
              AND b.isActive = true
            ORDER BY b.sortOrder ASC
            """)
    List<CustomButton> findResolvedButtons(Long websiteId, Long pageId);
}
