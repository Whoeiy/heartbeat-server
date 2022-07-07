package com.example.heartbeatserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.heartbeatserver.entity.MallCustomer;
import com.example.heartbeatserver.service.MallCustomerService;
import com.example.heartbeatserver.dao.MallCustomerMapper;
import org.springframework.stereotype.Service;

/**
* @author lihanbin
* @description 针对表【mall_customer】的数据库操作Service实现
* @createDate 2022-07-07 08:41:16
*/
@Service
public class MallCustomerServiceImpl extends ServiceImpl<MallCustomerMapper, MallCustomer>
    implements MallCustomerService{

}




