package com.company.entity;

import com.company.enums.HomeAttachStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "home_attach")
@Entity
public class HomeAttachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "home_id")
    private String homeId;

    @JoinColumn(name = "home_id", updatable = false, insertable = false)
    @ManyToOne
    private HomeEntity home;

    @Column(name = "attach_id")
    private String attachId;

    @JoinColumn(name = "attach_id", updatable = false, insertable = false)
    @OneToOne
    private AttachEntity attach;

    @Column
    @Enumerated(EnumType.STRING)
    private HomeAttachStatus status;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private Boolean visible;

}
