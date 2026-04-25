package com.desss.zcms.repository;

import com.desss.zcms.entity.MailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailConfigurationRepository extends JpaRepository<MailConfiguration, Long> {
    Optional<MailConfiguration> findByWebsiteIdAndStatus(Long websiteId, Integer status);
}
