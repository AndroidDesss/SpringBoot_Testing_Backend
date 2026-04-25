package com.desss.zcms.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PageComponentResponse {
    Long id;
    Long pageId;
    Long websiteId;
    String componentType;
    String componentData;
    Integer sortOrder;
    Integer status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
