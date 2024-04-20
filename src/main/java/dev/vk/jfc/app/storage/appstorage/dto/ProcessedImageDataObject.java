package dev.vk.jfc.app.storage.appstorage.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_processed_image_data")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
public class ProcessedImageDataObject extends HeadersObject {

    public ProcessedImageDataObject() {
        setMessageType("type-ProcessedImageDataObject");
    }

    private String marker_ProcessedImageDataObject;

//    private ProcessedImageObject processedImage;
}
