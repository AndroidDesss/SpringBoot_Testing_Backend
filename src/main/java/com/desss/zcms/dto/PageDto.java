package com.desss.zcms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageDto {
    Long id;
    Long websiteId;
    String title;
    String url;
    String pageExtension;
    String pageComponents;
    Integer externalPage;
    Integer status;
    Integer homeStatus;
    Integer publish;
    Integer isSitemap;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
