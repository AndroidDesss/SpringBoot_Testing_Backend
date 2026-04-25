package com.desss.zcms.repository;

import com.desss.zcms.entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
    List<Newsletter> findByWebsiteIdAndIsDeletedOrderByCreatedAtDesc(Long websiteId, Integer isDeleted);

    long countByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
