package com.desss.zcms.repository;

import com.desss.zcms.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted, Pageable pageable);

    List<Blog> findByWebsiteIdAndCategoryIdAndIsDeletedOrderBySortOrderAsc(Long websiteId, Long categoryId, Integer isDeleted);

    Optional<Blog> findByWebsiteIdAndUrlAndIsDeleted(Long websiteId, String url, Integer isDeleted);

    long countByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
