package com.company;

import com.company.repository.HomeAttachRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Building2ApplicationTests {

    @Autowired
    private HomeAttachRepository homeAttachRepository;

    @Test
    void contextLoads() {
        for (String s : homeAttachRepository
                .attachesOfHomeById("8a8a83d184e790e40184e791964a0000")) {
            System.out.println("s = " + s);
        }

    }

}
