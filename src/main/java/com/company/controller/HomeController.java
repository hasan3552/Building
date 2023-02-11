package com.company.controller;

import com.company.dto.home.HomeFULLDTO;
import com.company.dto.ResponseDTO;
import com.company.dto.home.HomeCreateDTO;
import com.company.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/public")
    public ResponseEntity<?> getById(@RequestParam("id") String id){

        HomeFULLDTO homeFULLDTO = homeService.getById(id);
        return ResponseEntity.ok(homeFULLDTO);
    }

    @GetMapping("/public/pagination")
    public ResponseEntity<?> getPagination(@RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size){

        PageImpl pagination = homeService.getPagination(page, size);
        return ResponseEntity.ok(pagination);
    }

    @PostMapping("/comp/create")
    public ResponseEntity<?> created(@RequestBody HomeCreateDTO dto){

        ResponseDTO responseDTO = homeService.create(dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/comp")
    public ResponseEntity<?> deleted(@RequestParam("id") String id){

        ResponseDTO deleted = homeService.deleted(id);
        return ResponseEntity.ok(deleted);
    }

}
