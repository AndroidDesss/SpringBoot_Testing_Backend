package com.desss.zcms.service;

import com.desss.zcms.dto.ContactInfoRequest;
import com.desss.zcms.dto.ContactInfoResponse;
import com.desss.zcms.entity.ContactInformation;
import com.desss.zcms.repository.ContactInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// ─────────────────────────────────────────────────────────────────────────────
//  Contact Info Service
// ─────────────────────────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContactInfoService {
    private final ContactInformationRepository repo;

    @Transactional(readOnly = true)
    public Optional<ContactInfoResponse> get(Long websiteId) {
        return repo.findByWebsiteIdAndIsDeleted(websiteId, 0).map(this::toResponse);
    }

    public ContactInfoResponse upsert(ContactInfoRequest req) {
        ContactInformation ci = repo.findByWebsiteIdAndIsDeleted(req.getWebsiteId(), 0)
                .orElse(ContactInformation.builder().websiteId(req.getWebsiteId()).isDeleted(0).build());
        ci.setTitle(req.getTitle());
        ci.setPhoneNo(req.getPhoneNo());
        ci.setEmail(req.getEmail());
        ci.setAddress(req.getAddress());
        ci.setFax(req.getFax());
        ci.setWorkingHours(req.getWorkingHours());
        ci.setStatus(req.getStatus());
        return toResponse(repo.save(ci));
    }

    ContactInfoResponse toResponse(ContactInformation c) {
        ContactInfoResponse r = new ContactInfoResponse();
        r.setId(c.getId());
        r.setWebsiteId(c.getWebsiteId());
        r.setTitle(c.getTitle());
        r.setPhoneNo(c.getPhoneNo());
        r.setEmail(c.getEmail());
        r.setAddress(c.getAddress());
        r.setFax(c.getFax());
        r.setWorkingHours(c.getWorkingHours());
        r.setStatus(c.getStatus());
        return r;
    }
}
