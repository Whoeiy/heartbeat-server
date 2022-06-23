package com.example.heartbeatserver;

import com.example.heartbeatserver.service.Impl.EsGiftServiceImpl;
import com.example.heartbeatserver.service.Impl.IndexInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HeartbeatServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private IndexInfoServiceImpl indexInfoService;

    @Test
    void getIndexInfos() {
//        System.out.println(this.indexInfoService.getIndexLabels());
        System.out.println(this.indexInfoService.getIndexConfigGifts(2));
    }

    @Autowired
    private EsGiftServiceImpl esGiftService;

    @Test
    void testImportAll(){
        System.out.println(this.esGiftService.importAll());
    }




}
