package dev.vk.jfc.app.storage.appstorage.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_processed_images")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
public class ProcessedImageObject extends HeadersObject {

    public ProcessedImageObject() {
        setMessageType("type-ProcessedImageObject");
    }

    private String marker_ProcessedImageObject;

//    @OneToOne
//    private ProcessedImageBndBoxesObject bndBoxes;
//    private ProcessedImageDataObject imageData;
}
