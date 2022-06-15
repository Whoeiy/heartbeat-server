package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.dao.CustomerDao;
import com.example.heartbeatserver.entity.Customer;
import com.example.heartbeatserver.entity.CustomerToken;
import com.example.heartbeatserver.service.ICustomerService;
import com.example.heartbeatserver.util.JwtUtil;
import com.example.heartbeatserver.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String customerRegister(Customer customer) {
        // 密码加密
        // String pswd = customer.getPassword();
        // customer.setPassword(Md5Util.getMd5(pswd));
        if (this.customerDao.addCustomer(customer) > 0) {
//            customer = this.customerDao.getCustomerByPhone(customer.getPhone());
//            // 生成token
//            String token = jwtUtil.createToken(customer);
//            if (this.customerDao.addCustomerToken(customer.getCustomerId(), token) > 0) {
//                return ServiceResultEnum.SUCCESS.getResult();
//            }
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        return this.customerDao.getCustomerByPhone(phone);
    }

    @Override
    public String customerLogin(String phone, String passwordMd5) {
        Customer customer = this.customerDao.getCustomerByPhone(phone);
        if (customer == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        // 校验用户手机号和密码是否匹配
        if (customer.getPhone().equals(phone) && customer.getPassword().equals(passwordMd5)) {
            // 匹配
            CustomerToken customerToken = this.customerDao.getCustomerTokenByCustomerId(customer.getCustomerId());
            // 生成token
            String token = jwtUtil.createToken(customer);
            if (customerToken == null) {
                // 新增token
                if (this.customerDao.addCustomerToken(customer.getCustomerId(), token) > 0) {
                    return token;
                }
                return ServiceResultEnum.DB_ERROR.getResult();
            }
            // 更新token
            if (this.customerDao.updateCustomerToken(customer.getCustomerId(), token) > 0) {
                return token;
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        // 不匹配
        return ServiceResultEnum.PHONE_PSWD_INCORRECT.getResult();
    }

    @Override
    public String customerLogout(Integer customerId) {
        if (this.customerDao.deleteCustomerToken(customerId) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
