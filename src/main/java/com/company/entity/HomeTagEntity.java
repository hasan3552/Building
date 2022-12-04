package com.company.entity;

import com.company.enums.HomeTagStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "home_tag")
public class HomeTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JoinColumn(name = "tag_id", nullable = false)
    @ManyToOne(targetEntity = TagEntity.class)
    private TagEntity tag;

    @JoinColumn(name = "home_id", nullable = false)
    @OneToOne(targetEntity = HomeEntity.class)
    private HomeEntity home;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HomeTagStatus status = HomeTagStatus.ACTIVE;

    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;

}
