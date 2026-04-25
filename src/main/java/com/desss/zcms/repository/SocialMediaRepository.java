package com.desss.zcms.repository;

import com.desss.zcms.entity.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    List<SocialMedia> findByWebsiteIdAndIsDeletedOrderBySortOrderAsc(Long websiteId, Integer isDeleted);
}
