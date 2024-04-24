package dev.vk.jfc.app.storage.appstorage.entities;

import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_float_array_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloatArrayItemEntity {

    @EmbeddedId
    private ArrayItemId itemId;

    private float val;
}
