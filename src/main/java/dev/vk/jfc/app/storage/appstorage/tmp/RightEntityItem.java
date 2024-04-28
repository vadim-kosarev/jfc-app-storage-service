package dev.vk.jfc.app.storage.appstorage.tmp;

import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_tmp_one_to_one_right_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RightEntityItem {
    @EmbeddedId
    private ArrayItemId id;
    private String itemData;
}
