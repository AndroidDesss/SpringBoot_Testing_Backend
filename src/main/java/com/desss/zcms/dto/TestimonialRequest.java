package com.desss.zcms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// ── Testimonial ───────────────────────────────────────────────────────────────
@Data
public class TestimonialRequest {
    @NotNull
    Long websiteId;
    String image;
    String imageAlt;
    @NotBlank
    String author;
    String designation;
    @NotBlank
    String content;
    String redirectUrl;
    Integer sortOrder = 0;
    Integer status = 1;
}
