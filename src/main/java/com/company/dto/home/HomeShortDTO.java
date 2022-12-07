package com.company.dto.home;

import com.company.entity.ProfileEntity;
import com.company.enums.BuildStatus;
import com.company.enums.HomeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class HomeShortDTO {

    private String id;
    private LocalDateTime createdDate;
    private Double area;
    private Double summa;
    private Integer countRoom;
    private Integer floor;
    private Integer totalFloor;
    private BuildStatus buildStatus;
    private String url;

}
