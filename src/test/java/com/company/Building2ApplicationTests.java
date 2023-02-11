package com.company;

import com.company.repository.HomeAttachRepository;
import com.company.service.AttachService;
import com.company.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
class Building2ApplicationTests {

    @Autowired
    private HomeAttachRepository homeAttachRepository;
    @Autowired
    private AttachService attachService;

    @Autowired
    private StorageService storageService;

    @Test
    void contextLoads() {
        for (String s : homeAttachRepository
                .attachesOfHomeById("8a8a83d184e790e40184e791964a0000")) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void attachDownload(){
        byte[] download = storageService.download("1670576384184_download.jfif");

        System.out.println("download.length = " + download.length);
    }


    @Test
    void openAttach(){
        Resource resource = attachService.downloadAWS("8a8a83d184f61a080184f61cd3480000");

        System.out.println("download.length = " +resource.exists());
    }

}
