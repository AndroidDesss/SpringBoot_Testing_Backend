package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.EventRequest;
import com.desss.zcms.dto.EventResponse;
import com.desss.zcms.dto.PaginatedResponse;
import com.desss.zcms.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Event
// ─────────────────────────────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
@Tag(name = "Admin - Events", description = "Manage events")
@SecurityRequirement(name = "bearerAuth")
public class AdminEventController {
    private final EventService service;

    @Operation(summary = "List events (paginated)")
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<EventResponse>>> list(
            @RequestParam Long websiteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(websiteId, page, size)));
    }

    @Operation(summary = "Create event")
    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> create(@Valid @RequestBody EventRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Event created", service.create(req)));
    }

    @Operation(summary = "Update event")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> update(@PathVariable Long id, @Valid @RequestBody EventRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Event updated", service.update(id, req)));
    }

    @Operation(summary = "Delete event (soft delete)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Event deleted", null));
    }
}
