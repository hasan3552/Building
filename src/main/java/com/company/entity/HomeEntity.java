package com.company.entity;

import com.company.enums.HomeStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity
public class HomeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "content")
    private String content;

    @Column(name = "location_id")
    private Integer location_id;

    @JoinColumn(name = "location_id", nullable = false, updatable = false, insertable = false)
    @OneToMany
    private LocationEntity location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_id")
    private Integer profile_id;

    @JoinColumn(name = "profile_id", nullable = false, updatable = false, insertable = false)
    @OneToMany
    private ProfileEntity profile;

    @Column(name = "home_status")
    @Enumerated(value = EnumType.STRING)
    private HomeStatus status;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    





}
