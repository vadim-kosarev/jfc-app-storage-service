package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrayItemId implements Serializable {
    private UUID objID;
    private Integer ind;
}
