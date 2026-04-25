package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.FaqResponse;
import com.desss.zcms.service.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/faqs")
@RequiredArgsConstructor
@Tag(name = "Public - FAQs", description = "Public FAQ access")
public class PublicFaqController {
    private final FaqService service;

    @Operation(summary = "List FAQs by page (public)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FaqResponse>>> list(@RequestParam Long pageId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getByPage(pageId)));
    }
}
