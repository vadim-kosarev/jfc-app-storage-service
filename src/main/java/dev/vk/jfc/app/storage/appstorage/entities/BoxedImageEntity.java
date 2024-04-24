package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Setter;

@Entity
@Table(name = "tbl_boxed_images")

@Setter
public class BoxedImageEntity extends HeadersEntity {

    private Integer faceNo;

    public BoxedImageEntity() {
        setMessageType("new-x-boxed-image");
    }

}
