package com.desss.zcms.repository;

import com.desss.zcms.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsernameAndIsDeleted(String username, Integer isDeleted);

    Optional<AdminUser> findByUsername(String username);

    List<AdminUser> findByWebsiteIdAndIsDeleted(Long websiteId, Integer isDeleted);
}
