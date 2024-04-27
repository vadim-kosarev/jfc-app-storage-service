package dev.vk.jfc.app.storage.appstorage.rest.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private String id;
    private String label;
    private String hostname;
    private String s3Path;
    private String source;
    private String messageType;
    private Long brokerTimestamp;
    private Long timestamp;
    private Integer localID;
    private Long created_dt;
}
