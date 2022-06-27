package com.example.heartbeatserver;

import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.service.Impl.CartServiceImpl;
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

    @Autowired
    private CartServiceImpl cartService;

    @Test
    void testAddGiftIntoCart(){
        AddGiftIntoCartParam param = new AddGiftIntoCartParam();
        param.setGiftId(18);
        param.setCount(1);
        param.setPrice(80.0);
        String res = cartService.addGiftIntoCart(2, param);
        System.out.println(res);
    }

    @Test
    void testShowCart() {
        System.out.println(this.cartService.showCart(2));
    }

    @Test
    void testShowCartVo(){
        System.out.println(this.cartService.showCartVo(2));
    }

    @Test
    void testUpdateGiftDes(){
        System.out.println(this.cartService.updateGiftDesRedis());
    }




}
