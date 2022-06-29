package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.controller.vo.AddressVo;
import com.example.heartbeatserver.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {

    // 新增地址
    Integer addNewAddress(Address address);

    // 查询默认地址Id
    Integer getDefaultAddressId();

    // 更新默认地址为非默认地址
    Integer updateDefaultAddressFlag(Integer addressId);

    // 查询地址列表
    List<AddressVo> getAddressList(Integer customerId);

    // 查询地址详情
    Address getAddress(Integer addressId);

    // 更新地址
    Integer updateAddress(Address address);

    // 删除地址
    Integer delAddress(Integer addressId);


}
