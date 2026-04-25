package com.desss.zcms.dto;

import lombok.Data;
import java.util.List;

@Data
public class PageComponentBulkRequest {
    Long pageId;
    Long websiteId;
    List<PageComponentRequest> components;
}
