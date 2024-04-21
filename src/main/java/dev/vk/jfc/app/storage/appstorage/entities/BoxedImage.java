package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_boxed_image")

@Data
public class BoxedImage extends HeadersEntity {

    private Integer faceNo;

    public BoxedImage() {
        setMessageType("x-boxed-image");
    }

}
