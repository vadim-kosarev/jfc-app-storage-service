package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_indexed_data")

public class IndexedData extends HeadersEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "base_object_parent1")
    private ProcessedImage parentImage;

    private Boolean markerImageIndexed;

}
