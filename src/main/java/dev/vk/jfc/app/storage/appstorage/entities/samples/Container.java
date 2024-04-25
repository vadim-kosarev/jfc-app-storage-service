package dev.vk.jfc.app.storage.appstorage.entities.samples;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "__tst_tbl_container")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Container {

    @Id
    @Column(name = "col_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "col_label")
    private String label;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "container")
    private Collection<Child> children;
}
