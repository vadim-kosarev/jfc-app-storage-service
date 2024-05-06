package dev.vk.jfc.app.storage.appstorage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ImageDto {

    private Long brokerTimestamp;
    private Long timestamp;
    private Integer frameNo;
    private String hostname;
    private Integer localID;
    private String s3Path;
    private String source;
    private String messageType;
    private String label;
    private UUID id;
    private String container;

    private List<ImageDto> elements;
    private Long created_dt;

    private IndexedDataDto indexedData;

}
