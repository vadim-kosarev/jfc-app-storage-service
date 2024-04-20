package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_processed_image_data")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
public class ImageData extends HeadersObject {

    public ImageData() {
        setMessageType("type-ProcessedImageDataObject");
    }

    private String marker_ProcessedImageDataObject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgdata_image", referencedColumnName = "base_id")
    private ProcessedImage processedImage;
}
