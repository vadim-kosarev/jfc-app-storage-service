package dev.vk.jfc.app.storage.appstorage.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contained")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contained {

    @Id
    @Column(name = "col_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "col_label")
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_parentID", referencedColumnName = "col_id")
    private Contained parent;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_id", referencedColumnName = "col_parentID")
    private Set<Contained> children;

}
