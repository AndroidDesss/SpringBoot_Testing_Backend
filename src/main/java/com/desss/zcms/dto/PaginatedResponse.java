package com.desss.zcms.dto;

import lombok.Data;

import java.util.List;

// ── Paginated response ────────────────────────────────────────────────────────
@Data
public class PaginatedResponse<T> {
    List<T> content;
    int page;
    int size;
    long totalElements;
    int totalPages;
    boolean last;

    public static <T> PaginatedResponse<T> of(
            org.springframework.data.domain.Page<T> page
    ) {
        PaginatedResponse<T> r = new PaginatedResponse<>();
        r.content = page.getContent();
        r.page = page.getNumber();
        r.size = page.getSize();
        r.totalElements = page.getTotalElements();
        r.totalPages = page.getTotalPages();
        r.last = page.isLast();
        return r;
    }
}
