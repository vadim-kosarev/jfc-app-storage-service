package dev.vk.jfc.app.storage.appstorage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "tbl_indexed_data")
@Setter
@Getter
public class IndexedDataEntity extends HeadersEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_parent")
    @JsonIgnore
    private ImageEntity imageEntity;

    private Boolean markerImageIndexed;

    /*
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentImageData")
    private Collection<IndexedDataItemEntity> items;
    */

    public IndexedDataEntity() {
        setMessageType(this.getClass().getSimpleName());
    }
}
