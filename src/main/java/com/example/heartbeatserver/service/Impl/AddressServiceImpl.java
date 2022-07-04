package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.vo.AddressVo;
import com.example.heartbeatserver.dao.AddressDao;
import com.example.heartbeatserver.entity.Address;
import com.example.heartbeatserver.service.AddressService;
import com.example.heartbeatserver.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public String addNewAddress(Address address) {
        // 判断该地址是否要设为默认地址
        if (address.getDefaultFlag() == 1) {
            Integer defaultAdrId = this.addressDao.getDefaultAddressId(address.getCustomerId());
            if (defaultAdrId != null) {
                // 默认地址已存在, 更新原默认地址defaultFlag = 0
                this.addressDao.updateDefaultAddressFlag(defaultAdrId);
            }
        }
        if (this.addressDao.addNewAddress(address) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return this.addressDao.getAddress(addressId);
    }

    @Override
    public String updateAddress(Address address) {
        Address adr = this.getAddressById(address.getAddressId());
        if (adr == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        // 地址状态由非默认地址更新为默认地址
        if (adr.getDefaultFlag() == 0 && address.getDefaultFlag() == 1) {
            Integer defaultAdrId = this.addressDao.getDefaultAddressId(address.getCustomerId());
            if (defaultAdrId != null) {
                // 默认地址已存在, 更新原默认地址defaultFlag = 0
                this.addressDao.updateDefaultAddressFlag(defaultAdrId);
            }
        }
        if (this.addressDao.updateAddress(address) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public List<AddressVo> getAddressList(Integer customerId) {
        List<AddressVo> list = this.addressDao.getAddressList(customerId);
        return list;
    }

    @Override
    public String delAddress(Integer addressId) {
        if (this.addressDao.getAddress(addressId) == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (this.addressDao.delAddress(addressId) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public AddressVo getDefaultAddressById(Integer customerId) {
        return this.addressDao.getDefaultAddress(customerId);
    }
}
