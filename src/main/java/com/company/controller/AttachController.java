package com.company.controller;

import com.company.dto.ResponseDTO;
import com.company.dto.attach.AttachDTO;
import com.company.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

//    @PostMapping("/upload")
//    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
//                                         HttpServletRequest request) {
//
//        Integer profileId = HttpHeaderUtil.getId(request);
//        AttachDTO dto = attachService.saveToSystem(file, profileId);
//        return ResponseEntity.ok().body(dto);
//    }d

//  ---------------------------  POST  ---------------------------------------
    @PostMapping("/upload/profile")
    public ResponseEntity<?> uploadProfile(@RequestParam("file") MultipartFile file) {

        AttachDTO dto = attachService.saveToSystemForProfile(file);
        return ResponseEntity.ok().body(dto);
    }

    //  -------------------------  GET  ---------------------------------------
    @GetMapping(value = "/open", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@RequestParam("fileId") String fileUUID) {
        return attachService.openGeneral(fileUUID);
    }


    @GetMapping(value = "/open/{fileId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileId") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("fileId") String fileId) {

        Resource file = attachService.download(fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }

    //    @GetMapping(value = "/open/{fileId}", produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] open(@PathVariable("fileId") String fileName) {
//        if (fileName != null && fileName.length() > 0) {
//            try {
//                return this.attachService.loadImage(fileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new byte[0];
//            }
//        }
//        return null;
//    }

    // -------------------------    DELETED  ---------------------------------
    @DeleteMapping("/profile")
    public ResponseEntity<?> deletedProfile() {

        ResponseDTO response = attachService.deletedProfile();

        return ResponseEntity.ok(response);
    }
    // -----------------------  PAGINATION  ----------------------------------------
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        PageImpl pagination = attachService.pagination(page, size);

        return ResponseEntity.ok(pagination);
    }


}
