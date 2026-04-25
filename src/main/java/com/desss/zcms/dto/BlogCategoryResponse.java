package com.desss.zcms.dto;

import lombok.Data;

@Data
public class BlogCategoryResponse {
    Long id;
    Long websiteId;
    String categoryName;
    String url;
    Integer status;
    Long blogCount;
}
