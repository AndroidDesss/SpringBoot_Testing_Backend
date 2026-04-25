package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.EventResponse;
import com.desss.zcms.dto.PaginatedResponse;
import com.desss.zcms.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/events")
@RequiredArgsConstructor
@Tag(name = "Public - Events", description = "Public event access")
public class PublicEventController {
    private final EventService service;

    @Operation(summary = "List events (public, paginated)")
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<EventResponse>>> list(
            @RequestParam Long websiteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId, page, size)));
    }
}
