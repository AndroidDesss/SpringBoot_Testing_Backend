package com.desss.zcms.repository;

import com.desss.zcms.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
    List<ContactUs> findByWebsiteIdAndIsDeletedOrderByIdDesc(Long websiteId, Integer isDeleted);

    long countByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
