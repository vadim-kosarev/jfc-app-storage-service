package dev.vk.jfc.app.storage.appstorage.tmp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "_tmp_one_to_one_right")
@Data
public class OneToOneRightEntity {
    @Id
    private UUID id;
    private String rightData;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "col_leftEntity")
    private OneToOneLeftEntity leftEntity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id.objID")
    private Collection<RightEntityItem> rightItems;

}
