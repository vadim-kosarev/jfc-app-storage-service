package dev.vk.jfc.app.storage.appstorage.tmp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "_tmp_one_to_one_left")
@Data
public class OneToOneLeftEntity {
    @Id
    private UUID id;
    private String leftData;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "leftEntity", cascade = CascadeType.ALL)
    private OneToOneRightEntity right;
}
