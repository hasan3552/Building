package com.company.dto.profile;

import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProfileCreateDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String phoneNumber;
    private String password;
    @NotBlank
    private String role;
    @NotNull
    private ProfileStatus status;

}
