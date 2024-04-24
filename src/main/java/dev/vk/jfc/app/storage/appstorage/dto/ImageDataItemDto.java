package dev.vk.jfc.app.storage.appstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ImageDataItemDto {
    private Integer faceIndex;
    private Float detection;
    private FaceBox faceBox;
    private double[] faceVector;
}
