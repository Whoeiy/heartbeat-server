package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author lihanbin
* @description 针对表【category】的数据库操作Mapper
* @createDate 2022-06-07 11:22:29
* @Entity generator.entity.Category
*/
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}




