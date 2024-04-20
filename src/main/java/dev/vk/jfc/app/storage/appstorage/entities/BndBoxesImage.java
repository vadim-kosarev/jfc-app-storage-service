package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_processed_images_boxes")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
public class BndBoxesImage extends HeadersObject {

    public BndBoxesImage() {
        setMessageType("type-ProcessedImageBndBoxesObject");
    }

    private String marker_ProcessedImageBndBoxesObject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bndbox_image", referencedColumnName = "base_id")
    private ProcessedImage processedImage;
}
