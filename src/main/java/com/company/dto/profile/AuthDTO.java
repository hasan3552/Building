package com.company.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {

    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 8)
    private String phoneNumber;

}
