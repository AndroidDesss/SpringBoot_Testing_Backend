package com.desss.zcms.repository;

import com.desss.zcms.entity.PaymentConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentConfigurationRepository
        extends JpaRepository<PaymentConfiguration, Long> {

    Optional<PaymentConfiguration> findByWebsiteIdAndStatus(Long websiteId, Integer status);
}
