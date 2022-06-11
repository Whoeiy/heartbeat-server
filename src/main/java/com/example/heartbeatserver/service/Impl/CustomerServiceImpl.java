package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.dao.CustomerDao;
import com.example.heartbeatserver.entity.Customer;
import com.example.heartbeatserver.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public String customerRegister(Customer customer) {
        if (this.customerDao.addCustomer(customer) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        return this.customerDao.getCustomerByPhone(phone);
    }
}
