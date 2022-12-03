package com.company.entity;

import com.company.enums.HomeStatus;
import com.company.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment_type")
public class PaymentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double defaultCash;

    @Column(nullable = false)
    private Double paymentTerm;

    @Column(nullable = false)
    private Double paymentCash;

    @Column(nullable = false)
    private Integer duration;

    @Column(name = "payment_status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "home_id")
    private String home_id;

    @JoinColumn(name = "home_id", nullable = false, updatable = false, insertable = false)
    @ManyToOne
    private HomeEntity home;


}
