package com.desss.zcms.repository;

import com.desss.zcms.entity.Seo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeoRepository extends JpaRepository<Seo, Long> {
    Optional<Seo> findByPageId(Long pageId);
}
