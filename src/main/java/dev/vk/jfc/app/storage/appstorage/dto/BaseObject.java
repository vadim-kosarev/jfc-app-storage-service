package dev.vk.jfc.app.storage.appstorage.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "tbl_base")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseObject {

    @Id
    @Column(name = "base_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "base_label")
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_object_parent")
    private BaseObject baseParent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "baseParent")
    private Collection<BaseObject> baseChildren;

}
