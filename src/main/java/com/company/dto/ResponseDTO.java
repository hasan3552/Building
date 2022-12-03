package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO  {

    private Integer status;
    private String message;

    public ResponseDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
