package dev.vk.jfc.app.storage.appstorage.entities;

import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "tbl_float_array_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloatArrayItemEntity {

    @EmbeddedId
    private ArrayItemId itemId;

    private Float val;

    public static class ElementsList extends ArrayList<FloatArrayItemEntity> {

        public ElementsList() {
        }

        public ElementsList(int initialCapacity) {
            super(initialCapacity);
        }
    }
}
