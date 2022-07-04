package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.controller.vo.NormalServiceVo;
import com.example.heartbeatserver.controller.vo.ServiceItemVo;
import com.example.heartbeatserver.dao.SettleDao;
import com.example.heartbeatserver.entity.CustomerService;
import com.example.heartbeatserver.service.SettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettleServiceImpl implements SettleService {

    @Autowired
    private SettleDao settleDao;


    public Map<Integer, String> getServiceMap() {
        Map<Integer, String> serviceMap = new HashMap<>();
        serviceMap.put(1, "定制卡片");
        serviceMap.put(2, "定制包装");
        serviceMap.put(3, "定制外观");
        serviceMap.put(4, "商家特供");
        return serviceMap;
    }


    @Override
    public List<NormalServiceVo> getNormalServiceList(Integer giftId) {
        List<CustomerService> serviceList = this.settleDao.getServiceList(giftId);
        if (serviceList == null) {
            return null;
        }

        List<NormalServiceVo> normalServiceVoList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            NormalServiceVo normalServiceVo = new NormalServiceVo();
            normalServiceVo.setServiceType(i);
            normalServiceVo.setServiceName(this.getServiceMap().get(i));
            normalServiceVo.setServiceItemVo(new ArrayList<>());
            for (CustomerService service : serviceList) {
                if (service.getServiceType() == normalServiceVo.getServiceType()) {
                    ServiceItemVo serviceItemVo = new ServiceItemVo();
                    serviceItemVo.setServiceId(service.getServiceId());
                    serviceItemVo.setServiceName(service.getServiceName());
                    serviceItemVo.setSampleImg(service.getSampleImg());
                    normalServiceVo.getServiceItemVo().add(serviceItemVo);
                }
            }
            normalServiceVoList.add(normalServiceVo);

        }
        return normalServiceVoList;
    }
}
