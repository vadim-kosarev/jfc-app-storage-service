package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "tbl_image_data_item")

@Data
public class ImageDataItem extends BaseObject {

    private Integer faceIndex;
    private Float detection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_parent_image_data", referencedColumnName = "base_id")
    private ImageData parentImageData;

    private Integer imgBox_p1_x;
    private Integer imgBox_p1_y;
    private Integer imgBox_p2_x;
    private Integer imgBox_p2_y;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemId.objID")
    private Collection<FloatArrayItem> faceVector;

}
