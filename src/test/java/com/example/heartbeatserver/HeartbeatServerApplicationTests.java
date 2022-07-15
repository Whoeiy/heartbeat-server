package com.example.heartbeatserver;

import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.dao.AddressDao;
import com.example.heartbeatserver.dao.CouponDao;
import com.example.heartbeatserver.service.Impl.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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

    @Test
    void testDelGift() {
        System.out.println(this.cartService.deleteGiftInCart(2,18));
    }

    @Test
    void testGetGiftDetail() {
        System.out.println(this.esGiftService.getGiftDetailById(17));
    }

    @Autowired
    private AddressDao addressDao;

    @Test
    void testAddressDao() {
//        System.out.println(this.addressDao.getDefaultAddressId(2));
        System.out.println(this.addressDao.getDefaultAddress(3));
    }

    @Autowired
    private SettleServiceImpl settleService;

    @Test
    void testService(){
        System.out.println(this.settleService.getNormalServiceList(33));
    }

    @Autowired
    private CouponDao couponDao;

    @Test
    void testCoupon(){
//        System.out.println(this.couponDao.getCustomerCouponList(10, new Date(1657106843)));
        System.out.println(this.couponDao.getCustomerCouponList(2, new Date()));
    }




}
