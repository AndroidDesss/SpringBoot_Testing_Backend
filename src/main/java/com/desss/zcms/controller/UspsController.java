package com.desss.zcms.controller;

import com.desss.zcms.dto.ApiResponse;
import com.desss.zcms.dto.UspsRateRequestDto;
import com.desss.zcms.dto.UspsRateResponseDto;
import com.desss.zcms.service.UspsShippingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shipping/usps")
@RequiredArgsConstructor

@PreAuthorize("hasRole('SUPER_ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class UspsController {
    private final UspsShippingService service;

    @PostMapping("/rates")
    public List<UspsRateResponseDto> getRates(
            @RequestBody UspsRateRequestDto request) {

        return service.getRates(request);
    }
}
