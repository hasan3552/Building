package com.company.service;

import com.company.entity.HomeAttachEntity;
import com.company.enums.HomeAttachStatus;
import com.company.repository.HomeAttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeAttachService {

    @Autowired
    private HomeAttachRepository homeAttachRepository;

    public String getAttachUrlFindAnyOne(String homeId) {

        System.out.println("getAttachUrlFull(homeId).size() = " + getAttachUrlFull(homeId).size());
        return getAttachUrlFull(homeId).get(0);
    }

    private List<String> getAttachUrlFull(String homeId) {

        return homeAttachRepository.attachesOfHomeById(homeId);
    }

    public void create(String homeId, List<String> attachesId) {

        attachesId.forEach(attachId -> {
            HomeAttachEntity homeAttach = new HomeAttachEntity();
            homeAttach.setAttachId(attachId);
            homeAttach.setHomeId(homeId);
            homeAttach.setCreatedDate(LocalDateTime.now());
            homeAttach.setVisible(Boolean.TRUE);
            homeAttach.setStatus(HomeAttachStatus.ACTIVE);

            homeAttachRepository.save(homeAttach);
        });
    }
}
