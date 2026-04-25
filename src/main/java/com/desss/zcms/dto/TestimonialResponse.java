package com.desss.zcms.dto;

import lombok.Data;

@Data
public class TestimonialResponse {
    Long id; Long websiteId;
    String image; String imageAlt;
    String author; String designation; String content;
    String redirectUrl;
    Integer sortOrder; Integer status;
}
