package com.company.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "attach")
@ToString
public class AttachEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(nullable = false)
    private String originalName;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long size;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


    public AttachEntity(String uuid) {
        this.uuid = uuid;
    }
}
