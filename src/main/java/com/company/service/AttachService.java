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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
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

    public byte[] openGeneral(String fileUUID) {
        AttachEntity attach = get(fileUUID);

        byte[] data;
        try {
            // fileName -> zari.jpg
            String path = "attaches/" + attach.getPath() + "/" + fileUUID + "." + attach.getExtension();
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public Resource download(String fileUUID) {

        AttachEntity attach = get(fileUUID);
        try {

            String path = "attaches/" + attach.getPath() + "/" + fileUUID + "." + attach.getExtension();
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

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
            dto.setPath(attach.getPath());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }

    public AttachDTO saveToSystemForProfile(MultipartFile file) {

        ProfileEntity profile = profileService.getProfile();

        AttachEntity attach = attachSaveFilesAndDB(file);
        AttachEntity oldAttach = null;
        if (profile.getPhoto() != null) {
            oldAttach = profile.getPhoto();
        }

        profile.setPhoto(attach);
        profileService.save(profile);

        if (oldAttach != null) {
            deletedFilesAndDB(oldAttach);
        }

        AttachDTO dto = new AttachDTO();
        dto.setUrl(serverUrl + "attach/open/" + attach.getUuid());
        return dto;
    }

    private void deletedFilesAndDB(AttachEntity attach) {
        try {
            Files.delete(Path.of(attachFolder + attach.getPath() + "/" + attach.getUuid() +
                    "." + attach.getExtension()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        attachRepository.delete(attach);
    }

    public AttachEntity attachSaveFilesAndDB(MultipartFile file) {

        File folder = new File(attachFolder + getYmDString());

        AttachEntity attach = new AttachEntity();
        attach.setExtension(getExtension(file.getOriginalFilename()));
        attach.setOriginalName(getOriginalName(file));
        attach.setSize(file.getSize());
        attach.setPath(getYmDString());
        attach.setFileName(file.getName());
        attachRepository.save(attach);

        String fileName = attach.getUuid() + "." + getExtension(file.getOriginalFilename());
        if (!folder.exists()) {
            folder.mkdirs();
        }

        byte[] bytes;
        try {
            bytes = file.getBytes();
            Path path = Paths.get(attachFolder + getYmDString() + "/" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return attach;
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
            profile.setPhoto(null);
            profileService.save(profile);
            deletedFilesAndDB(attach);
        }

        return new ResponseDTO(1, "success changeVisible");
    }

    public byte[] loadImage(String fileId) {
        byte[] imageInByte;

        Optional<AttachEntity> optional = attachRepository.findById(fileId);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Attach not found");
        }

        AttachEntity attach = optional.get();
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File("attaches/" + attach.getPath() + "/"
                    + attach.getUuid() + "." + attach.getExtension()));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, attach.getExtension(), baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageInByte;
    }
}
