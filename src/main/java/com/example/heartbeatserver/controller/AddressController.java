package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.AddressParam;
import com.example.heartbeatserver.controller.vo.AddressVo;
import com.example.heartbeatserver.entity.Address;
import com.example.heartbeatserver.service.AddressService;
import com.example.heartbeatserver.util.BeanUtil;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/a/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    @ApiOperation("/新增地址")
    public Result<String> addNewAddress(@RequestBody AddressParam param, Integer customerId) {
        Address address = new Address();
        BeanUtil.copyProperties(param, address);
        address.setCustomerId(customerId);
        address.setIsDeleted(0);

        String res = this.addressService.addNewAddress(address);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(res);
    }

    @GetMapping("/list")
    @ApiOperation("/查询地址列表")
    public Result<List<AddressVo>> getAddressList(Integer customerId) {
        List<AddressVo> list = this.addressService.getAddressList(customerId);
        if (list != null) {
            return ResultGenerator.genSuccessResultData(list);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    @GetMapping("/{addressId}")
    @ApiOperation("/查询地址信息")
    public Result<AddressVo> getAddressDetail(@PathVariable Integer addressId) {
        Address address = this.addressService.getAddressById(addressId);
        if (address != null) {
            AddressVo addressVo = new AddressVo();
            BeanUtil.copyProperties(address, addressVo);
            return ResultGenerator.genSuccessResultData(addressVo);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    @PutMapping
    @ApiOperation("/更新地址")
    public Result<String> updateAddress(@RequestBody AddressParam param) {
        Address address = new Address();
        BeanUtil.copyProperties(param, address);
        String res = this.addressService.updateAddress(address);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(res);
    }

    @DeleteMapping("/{addressId}")
    @ApiOperation("/删除地址")
    public Result<String> delAddress(@PathVariable Integer addressId) {
        String res = this.addressService.delAddress(addressId);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult(res);
    }
}
