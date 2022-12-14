package com.company.controller;

import com.company.dto.profile.*;
import com.company.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    //=========================== ADMIN ===============================
    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid ProfileCreateDTO dto) {
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id,
                                             @RequestBody @Valid ProfileCreateDTO dto) {
        ProfileDTO update = profileService.update(id, dto);
        return ResponseEntity.ok(update);
    }

    @GetMapping("/adm/pagination/list")
    public ResponseEntity<?> pagination(@RequestParam("size") Integer size,
                                        @RequestParam("page") Integer page) {

        PageImpl pagination = profileService.pagination(page, size);
        return ResponseEntity.ok(pagination);

    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Integer id) {

        ProfileDTO profileDTO = profileService.getProfile(id);

        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getProfileList() {

        List<ProfileDTO> profileDTOS = profileService.getAllProfileDTO();

        return ResponseEntity.ok(profileDTOS);
    }


    @DeleteMapping("/adm")
    public ResponseEntity<?> changeVisible(@RequestParam("id") Integer profileId) {

        ProfileDTO profileDTO = profileService.changeVisibleForAdmin(profileId);

        return ResponseEntity.ok(profileDTO);

    }

    //========================== PUBLIC ===============================

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> detailUpdate(@RequestBody @Valid ProfileUpdateDTO dto){

        ProfileDTO update = profileService.update(dto);
        return ResponseEntity.ok(update);
    }

//    @GetMapping("/profile")
//    public ResponseEntity<?> getProfile() {
//
//        ProfileDTO profileDTO = profileService.addAdmin();
//        return ResponseEntity.ok(profileDTO);
//    }

    @GetMapping("/profile/addminver")
    public ResponseEntity<?> getProfile() {

        ProfileDTO profileDTO = profileService.getProfilePublic();
        return ResponseEntity.ok(profileDTO);
    }

    @DeleteMapping("")
    public ResponseEntity<?> changeVisible() {

        ProfileDTO profileDTO = profileService.changeVisible();
        return ResponseEntity.ok(profileDTO);

    }

 //   @ApiOperation(value = "profile filter by admin")
//    @PostMapping("/adm/filter")/// profile/adm/filter
//    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto) {
//
//        List<ProfileShortDTO> response = profileService.filter(dto);
//        return ResponseEntity.ok().body(response);
//    }

}
