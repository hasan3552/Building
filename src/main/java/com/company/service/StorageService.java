package com.company.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucket;

    @Autowired
    private AmazonS3 s3Client;

    public String fileUpload(MultipartFile multipartFile) {
        File fileObj = convertMFileToFile(multipartFile);
        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj));
        System.out.println("fileName: " + fileName);
        fileObj.delete();
        return fileName;
    }

    public byte[] download(String fileName) {
        S3Object s3Object = s3Client.getObject(bucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucket, fileName);
        return fileName + ": removed";
    }

    private File convertMFileToFile(MultipartFile multipartFile) {
        File convertFile = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Error converting mFile to file");
        }
        return convertFile;
    }
}
