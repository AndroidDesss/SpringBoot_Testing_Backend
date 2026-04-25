package com.desss.zcms.dto;

import lombok.Data;

@Data
public class SeoResponse {
    Long id;
    Long pageId;
    String h1Tag;
    String h2Tag;
    String metaTitle;
    String metaDescription;
    String metaKeyword;
    String targetKeyword;
    String metaMisc;
}
