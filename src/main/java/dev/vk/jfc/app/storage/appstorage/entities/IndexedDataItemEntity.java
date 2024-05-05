package dev.vk.jfc.app.storage.appstorage.entities;

import dev.vk.jfc.app.storage.appstorage.dto.FaceBox;
import dev.vk.jfc.app.storage.appstorage.dto.ImagePoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_indexed_data_items")

@Setter
@Getter
@AllArgsConstructor
public class IndexedDataItemEntity extends HeadersEntity {

    private Integer faceIndex;
    private Float detection;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "col_parent", referencedColumnName = "base_id")
    private IndexedDataEntity parentImageData;

    private Integer imgBox_p1_x;
    private Integer imgBox_p1_y;
    private Integer imgBox_p2_x;
    private Integer imgBox_p2_y;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemId.objID")
    private List<FloatArrayItemEntity> faceVector = new ArrayList<>();

    public IndexedDataItemEntity() {
        faceVector = new ArrayList<>();
        setMessageType(this.getClass().getSimpleName());
    }


    // -----------------------------------------------------------------------------------------------------------------


    public static IndexedDataItemEntityBuilder builder() {
        return new IndexedDataItemEntityBuilder();
    }

    public static class IndexedDataItemEntityBuilder {
        private UUID id;
        private Integer faceIndex;
        private Float detection;
        private Integer imgBox_p1_x;
        private Integer imgBox_p1_y;
        private Integer imgBox_p2_x;
        private Integer imgBox_p2_y;
        private List<FloatArrayItemEntity> faceVector = new ArrayList<>();

        public IndexedDataItemEntityBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public IndexedDataItemEntityBuilder faceIndex(Integer faceIndex) {
            this.faceIndex = faceIndex;
            return this;
        }

        public IndexedDataItemEntityBuilder detection(Float arg) {
            this.detection = arg;
            return this;
        }

        public IndexedDataItemEntityBuilder imgBox_p1_x(Integer arg) {
            this.imgBox_p1_x = arg;
            return this;
        }

        public IndexedDataItemEntityBuilder imgBox_p1_y(Integer arg) {
            this.imgBox_p1_y = arg;
            return this;
        }

        public IndexedDataItemEntityBuilder imgBox_p2_x(Integer arg) {
            this.imgBox_p2_x = arg;
            return this;
        }

        public IndexedDataItemEntityBuilder imgBox_p2_y(Integer arg) {
            this.imgBox_p2_y = arg;
            return this;
        }

        public IndexedDataItemEntityBuilder faceVector(List<FloatArrayItemEntity> arg) {
            this.faceVector = arg;
            return this;
        }

        public IndexedDataItemEntity build() {
            IndexedDataItemEntity e = new IndexedDataItemEntity(
                    faceIndex, detection, null, imgBox_p1_x, imgBox_p1_y, imgBox_p2_x, imgBox_p2_y, faceVector
            );
            e.setId(id);
            return e;
        }
    }
}
