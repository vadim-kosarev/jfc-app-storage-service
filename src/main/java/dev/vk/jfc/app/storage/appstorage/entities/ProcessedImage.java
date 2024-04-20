package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_processed_images")

@Data
public class ProcessedImage extends HeadersObject {

    public ProcessedImage() {
        setMessageType("type-ProcessedImageObject");
    }

    private String marker_ProcessedImageObject;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processedImage")
    private BndBoxesImage imageBndBoxes;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processedImage")
    private ImageData imageData;
}
