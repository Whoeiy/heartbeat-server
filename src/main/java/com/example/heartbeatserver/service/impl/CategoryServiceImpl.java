package com.example.heartbeatserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.heartbeatserver.dao.CategoryMapper;
import com.example.heartbeatserver.entity.Category;
import com.example.heartbeatserver.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author lihanbin
* @description 针对表【category】的数据库操作Service实现
* @createDate 2022-06-07 11:22:29
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




