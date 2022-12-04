package com.company.dto;

import com.company.entity.HomeEntity;
import com.company.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter

public class PaymentTypeDTO {

    private Integer id;
    private Double defaultCash;
    private Double paymentTerm;
    private Double paymentCash;
    private Integer duration;
    private PaymentStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
//    private HomeShortDTO home;

}
