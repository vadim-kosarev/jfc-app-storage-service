package dev.vk.jfc.app.storage.appstorage.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_processed_images_boxes")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
public class ProcessedImageBndBoxesObject extends HeadersObject {

    public ProcessedImageBndBoxesObject() {
        setMessageType("type-ProcessedImageBndBoxesObject");
    }

    private String marker_ProcessedImageBndBoxesObject;

//    private ProcessedImageObject processedImage;
}
