package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_processed_images")
@Getter
@Setter
public class ProcessedImage extends HeadersEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentImage")
    private IndexedData indexedData;

    public ProcessedImage() {
        setMessageType("new-type-ProcessedImageObject");
    }

}
