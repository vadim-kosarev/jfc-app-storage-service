package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_indexed_data_items")
@Setter
@Getter
public class tmp_IndexedDataItem {

    @EmbeddedId
    private ArrayItemId itemId;

    private Boolean markerImageIndexedDataItem;

    private Integer faceIndex;

    private Float detection;
}
