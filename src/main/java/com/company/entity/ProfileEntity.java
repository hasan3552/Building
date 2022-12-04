package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.ACTIVE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileRole role = ProfileRole.CUSTOMER;

    @Column(nullable = false)
    private Boolean visible = true;

    @JoinColumn(name = "attach_id")
    @OneToOne(fetch = FetchType.EAGER)
    private AttachEntity photo;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    public ProfileEntity(Integer id) {
        this.id = id;
    }
}
