package com.company.service;

import com.company.dto.HomeFULLDTO;
import com.company.entity.HomeEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {


    @Autowired
    private ProfileService profileService;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private PaymentService paymentService;


    public HomeFULLDTO getDTO(HomeEntity entity) {

        HomeFULLDTO dto = new HomeFULLDTO();
        dto.setArea(entity.getArea());
        dto.setContent(entity.getContent());
        dto.setBuildStatus(entity.getBuildStatus());
        dto.setCountRoom(entity.getCountRoom());
        dto.setFloor(entity.getFloor());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfile(profileService.getShortDTO(entity.getProfile()));
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setStatus(entity.getStatus());
        dto.setSumma(entity.getSumma());
        dto.setVisible(entity.getVisible());
        dto.setUuid(entity.getUuid());
        dto.setPaymentTypeDTOS(paymentService.getAllPaymentByHomeId(entity.getUuid()));
        dto.setTotalFloor(entity.getTotalFloor());

        return dto;
    }

    public HomeEntity get(String uuid) {
        return homeRepository.findById(uuid).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
    }


}
