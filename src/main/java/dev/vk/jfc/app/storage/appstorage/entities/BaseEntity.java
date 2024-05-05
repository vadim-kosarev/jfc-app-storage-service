package dev.vk.jfc.app.storage.appstorage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_base")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.JOINED)

@Setter
@Getter
public class BaseEntity {

    @Id
    @Column(name = "base_id")
    private UUID id;

    @Column(name = "base_label")
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_parent")
    @JsonIgnore
    private BaseEntity container;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "container", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BaseEntity> elements;

    @Column(name = "created_dt", nullable = false)
    @Nullable
    private Long created_dt = System.currentTimeMillis();

    public BaseEntity() {
    }
}
