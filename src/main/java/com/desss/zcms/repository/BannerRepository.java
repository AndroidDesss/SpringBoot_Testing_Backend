package com.desss.zcms.repository;

import com.desss.zcms.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findByPageIdAndIsDeletedOrderBySortOrderAsc(Long pageId, Integer isDeleted);
}
