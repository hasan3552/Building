package com.company.entity;

import com.company.enums.LocationStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "locationn")
@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lang")
    private String lang;

    @Column(name = "late")
    private String late;

    @Column(name = "status")
    private LocationStatus status;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "home_id")
    private String home_id;

    @JoinColumn(name = "home_id", nullable = false, updatable = false, insertable = false)
    @OneToOne
    private HomeEntity home;

}
