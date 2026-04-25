package com.desss.zcms.repository;

import com.desss.zcms.entity.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long> {
    Optional<ContactInformation> findByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
