package com.desss.zcms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FaqResponse {
    Long id;
    Long pageId;
    String category;
    String faqQuestion;
    String faqAnswer;
    String sortOrder;
    Integer status;
    LocalDateTime createdAt;
}
