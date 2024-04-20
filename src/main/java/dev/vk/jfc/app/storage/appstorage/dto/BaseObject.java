package dev.vk.jfc.app.storage.appstorage.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tbl_base")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.JOINED)

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

    @Column(name = "created_dt", nullable = false)
    @Nullable
    private LocalDateTime created_dt = LocalDateTime.now();
}
