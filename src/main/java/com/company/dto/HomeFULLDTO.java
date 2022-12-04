package com.company.dto;

import com.company.dto.profile.ProfileShortDTO;
import com.company.entity.PaymentTypeEntity;
import com.company.enums.BuildStatus;
import com.company.enums.HomeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class HomeFULLDTO {

    private String uuid;
    private String content;
    private String phoneNumber;
    private ProfileShortDTO profile;
    private HomeStatus status;
    private Boolean visible;
    private LocalDateTime createdDate ;
    private Double area;
    private Double summa;
    private Integer countRoom;
    private Integer floor;
    private Integer totalFloor;
    private BuildStatus buildStatus;
    private List<PaymentShortDTO> paymentTypeDTOS;



}
