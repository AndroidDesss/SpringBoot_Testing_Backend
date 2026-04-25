package com.desss.zcms.repository;

import com.desss.zcms.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    List<Testimonial> findByWebsiteIdAndIsDeletedOrderBySortOrderAsc(Long websiteId, Integer isDeleted);
}
