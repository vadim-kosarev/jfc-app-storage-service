package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_processed_images")
@Getter
@Setter
public class ImageEntity extends HeadersEntity {


    /**
     * BaseObject: Container <---> Element[]
     *
     * One-to-Many: this image references recognized images - faces_image, [face_1, face_2, ..., face_n]
     *
     * Many-to-One: this image relates to recognized image using Container - Elements[] relations
     */

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "imageEntity")
    private IndexedDataEntity indexedDataEntity;

    public ImageEntity() {
        setMessageType(this.getClass().getSimpleName());
    }

}
