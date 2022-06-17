package com.example.heartbeatserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.heartbeatserver.controller.vo.FirstCategory;
import com.example.heartbeatserver.controller.vo.SecondLevelCategory;
import com.example.heartbeatserver.controller.vo.ThirdLevelCategory;
import com.example.heartbeatserver.entity.Category;
import com.example.heartbeatserver.service.CategoryService;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/a")
public class CategotryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/categories")
    public Result getAllLevel(Integer customerId){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDeleted", 0)
                .eq("categoryLevel",1)
                .orderByDesc("categoryRank")
                .orderByAsc("categoryID");
        List<Category> result = categoryService.list(queryWrapper);
        List<FirstCategory> firstCategories = new ArrayList<>();



        for (Category category : result) {
            FirstCategory firstCategory = new FirstCategory();
            firstCategory.setCategoryId(category.getCategoryid());
            firstCategory.setCategoryLevel(category.getCategorylevel());
            firstCategory.setCategoryName(category.getCategoryname());
//            System.out.println(firstCategory);
            queryWrapper.clear();
            queryWrapper.eq("isDeleted",0)
                    .eq("categoryLevel",2)
                    .eq("parentId", category.getCategoryid())
                    .orderByDesc("categoryRank")
                    .orderByAsc("categoryID");

            List<Category> result2 = categoryService.list(queryWrapper);
//            System.out.println(result2);
            ArrayList<SecondLevelCategory> secondLevelCategories = new ArrayList<SecondLevelCategory>();
            for (Category category2 : result2) {
                SecondLevelCategory secondLevelCategory = new SecondLevelCategory();
                secondLevelCategory.setCategoryId(category2.getCategoryid());
                secondLevelCategory.setParentId(category2.getParentid());
                secondLevelCategory.setCategoryLevel(category2.getCategorylevel());
                secondLevelCategory.setCategoryName(category2.getCategoryname());
//                System.out.println(secondLevelCategory);
                queryWrapper.clear();
                queryWrapper.eq("isDeleted",0)
                        .eq("categoryLevel",3)
                        .eq("parentId",category2.getCategoryid())
                        .orderByDesc("categoryRank")
                        .orderByAsc("categoryID");
                List<Category> result3 = categoryService.list(queryWrapper);
                ArrayList<ThirdLevelCategory> thirdLevelCategories = new ArrayList<ThirdLevelCategory>();
                for (Category category3 : result3) {
                    ThirdLevelCategory thirdLevelCategory = new ThirdLevelCategory();
                    thirdLevelCategory.setCategoryId(category3.getCategoryid());
                    thirdLevelCategory.setCategoryLevel(category3.getCategorylevel());
                    thirdLevelCategory.setCategoryName(category3.getCategoryname());
                    thirdLevelCategories.add(thirdLevelCategory);
                }

                secondLevelCategory.setThirdLevelCategoryVOS(thirdLevelCategories);
                secondLevelCategories.add(secondLevelCategory);
            }
            firstCategory.setSecondLevelCategoryVOS(secondLevelCategories);
//            System.out.println(firstCategory);
            firstCategories.add(firstCategory);
        }
        return ResultGenerator.genSuccessResultData(firstCategories);


    }
}
