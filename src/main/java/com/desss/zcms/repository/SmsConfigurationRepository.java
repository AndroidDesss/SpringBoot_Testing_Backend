package com.desss.zcms.repository;

import com.desss.zcms.entity.SmsConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SmsConfigurationRepository extends JpaRepository<SmsConfiguration, Long> {

    Optional<SmsConfiguration> findByWebsiteIdAndStatus(Long websiteId, Integer status);
}