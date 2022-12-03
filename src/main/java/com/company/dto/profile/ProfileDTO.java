package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {

    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;

    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private String url;

    private String jwt;
}
