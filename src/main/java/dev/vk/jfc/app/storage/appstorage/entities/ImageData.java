package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "tbl_processed_image_data")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
public class ImageData extends HeadersObject {

    private String marker_ProcessedImageDataObject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgdata_image", referencedColumnName = "base_id")
    private ProcessedImage processedImage;

    public ImageData() {
        setMessageType("type-ProcessedImageDataObject");
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentImageData")
    private Collection<ImageDataItem> dataItems;
}
