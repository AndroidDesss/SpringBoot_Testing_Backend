package com.desss.zcms.repository;

import com.desss.zcms.entity.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IntroductionRepository extends JpaRepository<Introduction, Long> {
    Optional<Introduction> findByPageId(Long pageId);
}