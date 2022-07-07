package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.MallCustomer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author lihanbin
* @description 针对表【mall_customer】的数据库操作Mapper
* @createDate 2022-07-07 08:41:16
* @Entity com.example.heartbeatserver.entity.MallCustomer
*/
@Mapper
@Repository
public interface MallCustomerMapper extends BaseMapper<MallCustomer> {

}




