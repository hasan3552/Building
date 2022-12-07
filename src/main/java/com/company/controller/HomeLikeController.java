package com.company.controller;

import com.company.dto.home.HomeLikeCreateDTO;
import com.company.service.HomeLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Home Like Controller")
@RestController
@RequestMapping("/home_like")
public class HomeLikeController {

    @Autowired
    private HomeLikeService homeLikeService;

    @ApiOperation(value = "Article Like", notes = "Article like by users")
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody HomeLikeCreateDTO dto) {
        log.info("Request for home like :{}", dto.getHomeId());
        homeLikeService.homeLike(dto.getHomeId());

        return ResponseEntity.ok("success");
    }

    @ApiOperation(value = "Article Dis Like", notes = "Article dis like by users")
    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody HomeLikeCreateDTO dto) {

        log.info("Request for dislike home :{}",dto.getHomeId());
        homeLikeService.homeDisLike(dto.getHomeId());

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Article Like Remove", notes = "Article like remove by users")
    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody HomeLikeCreateDTO dto) {

        log.info("Request for remove home like:{}",dto.getHomeId());
        homeLikeService.removeLike(dto.getHomeId());

        return ResponseEntity.ok().build();
    }
}
