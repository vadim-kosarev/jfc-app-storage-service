package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_processed_image_data")

@Setter
@Getter
public class ImageDataEntity extends HeadersEntity {

    private String marker_ProcessedImageDataObject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgdata_image", referencedColumnName = "base_id")
    private ProcessedImage processedImage;

    public ImageDataEntity() {
        setMessageType("new-type-ProcessedImageDataObject");
    }
}
