package dev.vk.jfc.app.storage.appstorage.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "_C")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contained {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "label")
    private String label;

    @ManyToOne
    @JoinColumn(name = "parentID", referencedColumnName = "id")
    private Contained parent;

}
