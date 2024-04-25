package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_indexed_data")
@Setter
@Getter
public class IndexedDataEntity extends HeadersEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "base_object_parent1")
    private ImageEntity parentImage;

    private Boolean markerImageIndexed;

}
