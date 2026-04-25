package com.desss.zcms.repository;

import com.desss.zcms.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {
    List<Website> findByIsDeleted(Integer isDeleted);

    Optional<Website> findByIdAndIsDeleted(Long id, Integer isDeleted);
}
