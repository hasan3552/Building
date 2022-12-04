package com.company.service;

import com.company.dto.PaymentShortDTO;
import com.company.dto.PaymentTypeDTO;
import com.company.entity.PaymentTypeEntity;
import com.company.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;


    public List<PaymentShortDTO> getAllPaymentByHomeId(String homeId) {

        List<PaymentTypeEntity> entityList = paymentTypeRepository
                .findAllByHome_idAAndVisible(homeId, Boolean.TRUE);

        List<PaymentShortDTO> paymentTypeDTOS = new ArrayList<>();
        entityList.forEach(entity -> paymentTypeDTOS.add(getDTO(entity)));

        return paymentTypeDTOS;
    }

    public PaymentShortDTO getDTO(PaymentTypeEntity entity){

        PaymentShortDTO paymentShortDTO = new PaymentShortDTO();

        paymentShortDTO.setPaymentCash(entity.getPaymentCash());
        paymentShortDTO.setPaymentTerm(entity.getPaymentTerm());
        paymentShortDTO.setCreatedDate(entity.getCreatedDate());
        paymentShortDTO.setDefaultCash(entity.getDefaultCash());
        paymentShortDTO.setStatus(entity.getStatus());
        paymentShortDTO.setVisible(entity.getVisible());
        paymentShortDTO.setId(entity.getId());
        paymentShortDTO.setDuration(entity.getDuration());

        return paymentShortDTO;
    }
}
