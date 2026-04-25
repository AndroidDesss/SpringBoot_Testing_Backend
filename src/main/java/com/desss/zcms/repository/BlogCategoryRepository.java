package com.desss.zcms.repository;

import com.desss.zcms.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
    List<BlogCategory> findByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
