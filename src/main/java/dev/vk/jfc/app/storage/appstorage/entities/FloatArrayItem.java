package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloatArrayItem {

    @EmbeddedId
    private FloatArrayItemId itemId;

    private float val;
}
