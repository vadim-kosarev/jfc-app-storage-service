package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tbl_children")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Child {

    @Id
    @Column(name = "child_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "child_str_label")
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_container")
    private Container container;

}
