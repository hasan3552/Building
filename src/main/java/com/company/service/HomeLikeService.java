package com.company.service;

import com.company.entity.HomeEntity;
import com.company.entity.HomeLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.HomeLikeRepository;
import com.company.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class HomeLikeService {
    @Autowired
    private HomeLikeRepository homeLikeRepository;
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private ProfileService profileService;

    public void homeLike(String homeId) {
        ProfileEntity profile = profileService.getProfile();
        likeDislike(homeId, profile.getId(), LikeStatus.LIKE);
    }

    public void homeDisLike(String homeId) {
        ProfileEntity profile = profileService.getProfile();
        likeDislike(homeId, profile.getId(), LikeStatus.DISLIKE);
    }

    private void likeDislike(String homeId, Integer pId, LikeStatus status) {
        Optional<HomeLikeEntity> optional = homeLikeRepository.findExists(homeId, pId);
        if (optional.isPresent()) {
            HomeLikeEntity like = optional.get();
            like.setStatus(status);
            homeLikeRepository.save(like);
            return;
        }
        boolean homeExists = homeRepository.existsById(homeId);
        if (!homeExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        HomeLikeEntity like = new HomeLikeEntity();
        like.setHome(new HomeEntity(homeId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        homeLikeRepository.save(like);
    }

    public void removeLike(String homeId) {
       /* Optional<HomeLikeEntity> optional = homeLikeRepository.findExists(homeId, pId);
        optional.ifPresent(homeLikeEntity -> {
            homeLikeRepository.delete(homeLikeEntity);
        });*/
        ProfileEntity profile = profileService.getProfile();
        homeLikeRepository.delete(homeId, profile.getId());
    }

    public Map<String, Integer> countLikeDislike(String homeId){

        return homeLikeRepository.countLikeDislike(homeId);
    }
}
