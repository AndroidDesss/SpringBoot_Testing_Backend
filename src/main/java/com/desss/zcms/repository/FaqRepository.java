package com.desss.zcms.repository;

import com.desss.zcms.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByPageIdAndIsDeletedOrderBySortOrderAsc(Long pageId, Integer isDeleted);
}
