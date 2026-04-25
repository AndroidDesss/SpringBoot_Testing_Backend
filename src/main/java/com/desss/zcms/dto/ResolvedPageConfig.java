package com.desss.zcms.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResolvedPageConfig {
    Long pageId;
    Long websiteId;
    /** All field definitions with labels already resolved (page overrides > global > default) */
    List<CustomFieldResponse> fields;
    /** All buttons assigned to this page (page-level + global) */
    List<CustomButtonResponse> buttons;
    /** Raw label map: fieldKey → resolved label */
    Map<String, String> labelMap;
}
