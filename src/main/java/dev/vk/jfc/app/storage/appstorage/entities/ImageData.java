package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "tbl_processed_image_data")

@Data
public class ImageData extends HeadersEntity {

    private String marker_ProcessedImageDataObject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgdata_image", referencedColumnName = "base_id")
    private ProcessedImage processedImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentImageData")
    private Collection<ImageDataItem> dataItems;

    public ImageData() {
        setMessageType("type-ProcessedImageDataObject");
    }
}
