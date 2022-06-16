package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.LabelNew;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author lihanbin
* @description 针对表【label_new】的数据库操作Mapper
* @createDate 2022-06-15 23:22:58
* @Entity com.example.heartbeatserver.entity.LabelNew
*/
@Mapper
@Repository
public interface LabelNewMapper extends BaseMapper<LabelNew> {

}




