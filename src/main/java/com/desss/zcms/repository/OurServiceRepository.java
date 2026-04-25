package com.desss.zcms.repository;

import com.desss.zcms.entity.OurService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OurServiceRepository extends JpaRepository<OurService, Long> {
    List<OurService> findByPageIdAndIsDeletedOrderBySortOrderAsc(Long pageId, Integer isDeleted);
}
