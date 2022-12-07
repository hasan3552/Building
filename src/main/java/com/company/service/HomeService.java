package com.company.service;

import com.company.dto.home.HomeFULLDTO;
import com.company.dto.ResponseDTO;
import com.company.dto.home.HomeCreateDTO;
import com.company.dto.home.HomeShortDTO;
import com.company.entity.HomeEntity;
import com.company.enums.HomeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class HomeService {


    @Autowired
    private ProfileService profileService;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private HomeAttachService homeAttachService;


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
        dto.setUuid(entity.getId());
        dto.setPaymentTypeDTOS(paymentService.getAllPaymentByHomeId(entity.getId()));
        dto.setTotalFloor(entity.getTotalFloor());

        return dto;
    }

    public HomeEntity get(String uuid) {
        return homeRepository.findById(uuid).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
    }


    public HomeFULLDTO getById(String id) {

        HomeEntity entity = get(id);
        return getDTO(entity);
    }

    public PageImpl getPagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<HomeEntity> all = homeRepository.findAll(pageable);

        List<HomeEntity> list = all.getContent();

        List<HomeShortDTO> dtoList = new LinkedList<>();

        list.forEach(home -> dtoList.add(getShortDTO(home)));

        return new PageImpl(dtoList, pageable, all.getTotalElements());

    }

    private HomeShortDTO getShortDTO(HomeEntity home) {

        HomeShortDTO dto = new HomeShortDTO();
        dto.setId(home.getId());
        dto.setArea(home.getArea());
        dto.setFloor(home.getFloor());
        dto.setSumma(home.getSumma());
        dto.setBuildStatus(home.getBuildStatus());
        dto.setCountRoom(home.getCountRoom());
        dto.setTotalFloor(home.getTotalFloor());
        dto.setCreatedDate(home.getCreatedDate());
        dto.setUrl(attachService.openUrl(homeAttachService.getAttachUrlFindAnyOne(home.getId())));

        return dto;
    }

    public ResponseDTO create(HomeCreateDTO dto) {

        HomeEntity entity = new HomeEntity();
        entity.setArea(dto.getArea());
        entity.setStatus(HomeStatus.ACTIVE);
        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setFloor(dto.getFloor());
        entity.setTotalFloor(dto.getTotalFloor());
        entity.setBuildStatus(dto.getBuildStatus());
        entity.setCountRoom(dto.getCountRoom());
        entity.setProfile_id(profileService.getProfile().getId());
        entity.setSumma(dto.getSumma());
        entity.setVisible(Boolean.TRUE);
        entity.setPhoneNumber(dto.getPhoneNumber());

        homeRepository.save(entity);
        homeAttachService.create(entity.getId(), dto.getAttachesId());
        paymentService.create(entity.getId(), dto.getPaymentTypesId());

        return new ResponseDTO(1, "success");
    }
}
