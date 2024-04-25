package dev.vk.jfc.app.storage.appstorage.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "tbl_headers")
@Setter
public class HeadersEntity extends BaseEntity {

    @Column(nullable = true)
//    private LocalDateTime brokerTimestamp = LocalDateTime.now();
    private Long brokerTimestamp = System.currentTimeMillis();


    @Column(nullable = true)
//    private LocalDateTime timestamp = LocalDateTime.now();
    private Long timestamp = System.currentTimeMillis();

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
