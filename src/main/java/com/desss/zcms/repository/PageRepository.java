package com.desss.zcms.repository;

import com.desss.zcms.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
    Optional<Page> findByWebsiteIdAndUrlAndIsDeleted(Long websiteId, String url, Integer isDeleted);
}
