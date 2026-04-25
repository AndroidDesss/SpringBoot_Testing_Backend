package com.desss.zcms.repository;

import com.desss.zcms.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted, Pageable pageable);

    Optional<Event> findByWebsiteIdAndUrlAndIsDeleted(Long websiteId, String url, Integer isDeleted);

    long countByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
