package dev.vk.jfc.app.storage.appstorage.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_headers")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeadersObject extends BaseObject {

    @Column(nullable = true)
    private LocalDateTime brokerTimestamp = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = true)
    private Integer faceNo;

    @Column(nullable = true)
    private Integer frameNo;

    @Column(nullable = true)
    private String hostname;

    @Column(name = "columnID")
    private Integer localID;

    @Column(nullable = true, length = 65535)
    private String s3Path;

    @Column(nullable = true, length = 65535)
    private String source;

    @Column(name = "messageType")
    private String messageType;

    private String marker_HeadersObject;

}
