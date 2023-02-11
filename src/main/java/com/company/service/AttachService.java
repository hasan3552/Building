package com.company.service;

import com.company.dto.ResponseDTO;
import com.company.dto.attach.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttachService {

    @Value("${attach.folder}")
    private String attachFolder;

    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private StorageService storageService;

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach Not found");
        });
    }

    public PageImpl pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "uuid");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> all = attachRepository.findAll(pageable);

        List<AttachEntity> list = all.getContent();

        List<AttachDTO> dtoList = new LinkedList<>();

        list.forEach(attach -> {
            AttachDTO dto = new AttachDTO();
            dto.setId(attach.getUuid());
            dto.setUrl(serverUrl + "attach/open?fileId=" + attach.getUuid());
            dto.setOriginalName(attach.getOriginalName());
            dto.setFileName(attach.getFileName());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }


    private String getOriginalName(MultipartFile file) {
        return file.getOriginalFilename()
                .replace(("." + getExtension(file.getOriginalFilename())), "");
    }

    public String openUrl(String uuid) {
        return (serverUrl + "attach/open/" + uuid);
    }

    public ResponseDTO deletedProfile() {

        ProfileEntity profile = profileService.getProfile();
        AttachEntity attach = profile.getPhoto();

        if (attach != null) {
            profile.setAttachId(null);
            profileService.save(profile);
            deletedAmazonAndDB(attach);
        }

        return new ResponseDTO(1, "success changeVisible");
    }

    private void deletedAmazonAndDB(AttachEntity attach) {
        attachRepository.delete(attach);
        storageService.deleteFile(attach.getFileName());
    }

    public byte[] loadImage(String fileId) {
        AttachEntity attach = get(fileId);
        String fileName = attach.getFileName();

        return storageService.download(fileName);
    }

    public String saveAmazonS3(MultipartFile file) {
        return storageService.fileUpload(file);
    }

    public String saveDB(String fileName, MultipartFile file) {

        AttachEntity attach = new AttachEntity();
        attach.setExtension(getExtension(file.getOriginalFilename()));
        attach.setOriginalName(getOriginalName(file));
        attach.setSize(file.getSize());
        attach.setPath(getYmDString());
        attach.setFileName(fileName);
        attachRepository.save(attach);

        return attach.getUuid();
    }

    public AttachDTO saveToAmazonAndDB(MultipartFile file) {
        String fileName = saveAmazonS3(file);
        String attachId = saveDB(fileName, file);

        return new AttachDTO(attachId, fileName);
    }

    public byte[] inputStream(AttachEntity attach) {
        byte[] buffer = storageService.download(attach.getFileName());

        return buffer;
    }

    public Resource downloadAWS(String attachId) {

        AttachEntity attach = get(attachId);
        byte[] buffer = inputStream(attach);

        File targetFile = new File("src/main/resources/" + attach.getFileName());
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path file = Paths.get(targetFile.getPath());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public AttachDTO saveToAmazonAndDBForProfile(MultipartFile file) {

        AttachDTO dto = saveToAmazonAndDB(file);
        ProfileEntity profile = profileService.getProfile();
        AttachEntity photo = profile.getPhoto();
        profile.setAttachId(dto.getId());
        profileService.save(profile);
        if(photo != null){
            deletedAmazonAndDB(photo);
        }

        return new AttachDTO(profile.getAttachId(), dto.getFileName());
    }

}
