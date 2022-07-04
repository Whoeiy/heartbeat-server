package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.AddressVo;
import com.example.heartbeatserver.entity.Address;

import java.util.List;

public interface AddressService {

    // 新增地址
    String addNewAddress(Address address);

    // 根据Id查询地址信息
    Address getAddressById(Integer addressId);

    // 更新地址
    String updateAddress(Address address);

    // 查询地址列表
    List<AddressVo> getAddressList(Integer customerId);

    // 删除地址
    String delAddress(Integer addressId);

    // 查询默认地址
    AddressVo getDefaultAddressById(Integer customerId);


}
