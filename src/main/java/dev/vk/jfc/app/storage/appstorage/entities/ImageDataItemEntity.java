package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "tbl_indexed_data_items")

@Setter
public class ImageDataItemEntity extends HeadersEntity {

    private Integer faceIndex;
    private Float detection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_parent_image", referencedColumnName = "base_id")
    private IndexedDataEntity parentImageData;

    private Integer imgBox_p1_x;
    private Integer imgBox_p1_y;
    private Integer imgBox_p2_x;
    private Integer imgBox_p2_y;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemId.objID")
    private Collection<FloatArrayItemEntity> faceVector;

}
