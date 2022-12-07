package com.company.dto.home;

import com.company.dto.profile.ProfileShortDTO;
import com.company.enums.BuildStatus;
import com.company.enums.HomeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class HomeCreateDTO {

    private Integer locationId;
    private List<PaymentCreateDTO> paymentTypesId;
    private List<String> attachesId;
    private String content;
    private String phoneNumber;
// profileId security
    private Double area;
    private Double summa;
    private Integer countRoom;
    private Integer floor;
    private Integer totalFloor;
    private BuildStatus buildStatus;

}
