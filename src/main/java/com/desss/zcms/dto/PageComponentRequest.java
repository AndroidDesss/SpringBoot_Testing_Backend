package com.desss.zcms.dto;

import lombok.Data;

@Data
public class PageComponentRequest {
    Long pageId;
    Long websiteId;
    String componentType;
    String componentData;
    Integer sortOrder;    // canvas position — used in bulk-save entity creation
}
