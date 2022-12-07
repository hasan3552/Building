package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.*;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import com.company.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    @Lazy
    private AttachService attachService;

    @Value("${server.url}")
    private String serverUrl;

    // ========================= ADMIN =================================
    public ProfileDTO create(ProfileCreateDTO dto) {
        // name; surname; login; password;
        Optional<ProfileEntity> optional = profileRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));

        entity.setRole(ProfileRole.valueOf(dto.getRole()));
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);
        return getProfileDTO(entity);
    }

    public ProfileDTO update(Integer profileId, ProfileCreateDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (optional.isPresent() && !optional.get().getId().equals(profileId)) {
            throw new BadRequestException("this phone already exist");
        }

        ProfileEntity entity = get(profileId);
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setStatus(dto.getStatus());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()){
            entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        }

        entity.setRole(ProfileRole.valueOf(dto.getRole()));
        profileRepository.save(entity);

        return getProfileDTO(entity);
    }

    public List<ProfileDTO> getAllProfileDTO() {

        Iterable<ProfileEntity> iterable = profileRepository.findAll();

        List<ProfileDTO> profileDTOS = new ArrayList<>();
        iterable.forEach(profileEntity -> {

            profileDTOS.add(getProfileDTO(profileEntity));
        });

        return profileDTOS;
    }
    // ========================= GENERAL ===============================

    public ProfileDTO update(ProfileUpdateDTO dto) {

        ProfileEntity entity = getProfile();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));

        profileRepository.save(entity);

        return getProfileDTO(entity);
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile Not found");
        });
    }

    public ProfileDTO getProfileDTO(ProfileEntity entity) {

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId(entity.getId());
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setPhoneNumber(entity.getPhoneNumber());
        profileDTO.setRole(entity.getRole());
        profileDTO.setStatus(entity.getStatus());
        profileDTO.setVisible(entity.getVisible());

        if (entity.getPhoto() != null) {
            profileDTO.setUrl(attachService.openUrl(entity.getPhoto().getUuid()));
        }

        return profileDTO;
    }

    public ProfileDTO changeVisible() {

        ProfileEntity entity = getProfile();
        entity.setVisible(!entity.getVisible());

        profileRepository.save(entity);

        return getProfileDTO(entity);
    }

    public ProfileDTO changeVisibleForAdmin(Integer pId) {

        ProfileEntity entity = get(pId);
        entity.setVisible(!entity.getVisible());

        profileRepository.save(entity);

        return getProfileDTO(entity);
    }

    public ProfileDTO getProfile(Integer id) {

        ProfileEntity profile = get(id);
        return getProfileDTO(profile);
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public void save(ProfileEntity profile) {
        profileRepository.save(profile);
    }

    public ProfileDTO getProfilePublic() {

        ProfileEntity profile = getProfile();
        return getProfileDTO(profile);
    }
//
//    public List<ProfileShortDTO> filter(ProfileFilterDTO dto) {
//        List<ProfileEntity> list = customProfileRepository.filter(dto);
//
//        List<ProfileShortDTO> profileShortList = new ArrayList<>();
//        list.forEach(entity -> {
//            profileShortList.add(getShortDTO(entity));
//        });
//
//        return profileShortList;
//    }

    public ProfileShortDTO getShortDTO(ProfileEntity entity) {
        ProfileShortDTO dto = new ProfileShortDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setPhoneNumber(entity.getPhoneNumber());
        if (entity.getPhoto() != null) {
            dto.setUrl(attachService.openUrl(entity.getPhoto().getUuid()));
        }

        return dto;
    }

   @PostConstruct
    private void setDefaultUsers() {
        if (profileRepository.count() == 0) {
            ProfileEntity user = new ProfileEntity();
            //List<Role> roles = roleRepository.findAll();
//            if (roles.size() > 0) {
//                user.setRoles(roles);
//            }

            user.setName("name");
            user.setSurname("lastName");
            user.setPassword(MD5Util.getMd5("admin"));
            user.setPhoneNumber("90909000");
            user.setRole(ProfileRole.ADMIN);
            user.setStatus(ProfileStatus.ACTIVE);
            user.setVisible(true);
            profileRepository.save(user);
        }
    }

    public PageImpl pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProfileEntity> all = profileRepository.findAll(pageable);

        List<ProfileEntity> list = all.getContent();

        List<ProfileDTO> dtoList = new LinkedList<>();

        list.forEach(profile -> dtoList.add(getProfileDTO(profile)));

        return new PageImpl(dtoList, pageable, all.getTotalElements());

    }
}