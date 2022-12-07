package com.company.dto.home;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCreateDTO {

    private Double defaultCash;
    private Double paymentTerm;
    private Double paymentCash;
    private Integer duration;

}
