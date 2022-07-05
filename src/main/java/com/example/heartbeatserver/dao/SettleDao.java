package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.CustomerService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SettleDao {

    List<CustomerService> getServiceList(Integer giftId);

    CustomerService getService(Integer serviceId);
}
