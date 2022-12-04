package com.company.entity;

import com.company.enums.BuildStatus;
import com.company.enums.HomeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HomeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "content")
    private String content;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_id")
    private Integer profile_id;

    @JoinColumn(name = "profile_id", nullable = false, updatable = false, insertable = false)
    @ManyToOne
    private ProfileEntity profile;

    @Column(name = "home_status")
    @Enumerated(value = EnumType.STRING)
    private HomeStatus status;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Double summa;

    @Column(nullable = false)
    private Integer countRoom;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer totalFloor;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BuildStatus buildStatus;

    public HomeEntity(String uuid) {
        this.uuid = uuid;
    }
}
