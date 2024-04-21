package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_processed_images")

public class ProcessedImage extends HeadersEntity {

    private String marker_ProcessedImageObject;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processedImage")
    private ImageData imageData;

    public ProcessedImage() {
        setMessageType("new-type-ProcessedImageObject");
    }

}
