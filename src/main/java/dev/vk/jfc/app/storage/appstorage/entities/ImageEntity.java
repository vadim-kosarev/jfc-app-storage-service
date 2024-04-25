package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_processed_images")
@Getter
@Setter
public class ImageEntity extends HeadersEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentImage")
    private IndexedDataEntity indexedDataEntity;

    public ImageEntity() {
        setMessageType("new-type-ProcessedImageObject");
    }

}
