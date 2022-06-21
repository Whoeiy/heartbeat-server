package com.example.heartbeatserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.heartbeatserver.controller.vo.*;
import com.example.heartbeatserver.entity.Category;
import com.example.heartbeatserver.entity.LabelNew;
import com.example.heartbeatserver.service.CategoryService;
import com.example.heartbeatserver.service.LabelNewService;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/a")
public class LabelController {
    @Autowired
    private LabelNewService labelNewService;


    @GetMapping("/labels")
    public Result getAllLevel(Integer customerId){
        QueryWrapper<LabelNew> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDeleted", 0)
                .eq("labelLevel",1)
                .eq("parentId", 0)
                .orderByDesc("labelRank")
                .orderByAsc("labelID");
        List<LabelNew> result = labelNewService.list(queryWrapper);
        List<FirstLabel>  firstLabels= new ArrayList<>();

        for (LabelNew label : result) {
            FirstLabel firstLabel = new FirstLabel();
            firstLabel.setLabelId(label.getLabelid());
            firstLabel.setLabelLevel(label.getLabellevel());
            firstLabel.setLabelName(label.getLabelname());


            queryWrapper.clear();
            queryWrapper.eq("isDeleted",0)
                    .eq("labelLevel",2)
                    .eq("parentId", label.getLabelid())
                    .orderByDesc("labelRank")
                    .orderByAsc("labelID");

            List<LabelNew> result2 = labelNewService.list(queryWrapper);
            ArrayList<SecondLevelLabel>  secondLevelLabels= new ArrayList<SecondLevelLabel>();
            for (LabelNew label2 : result2) {
                SecondLevelLabel secondLevelLabel = new SecondLevelLabel();
                secondLevelLabel.setLabelId(label2.getLabelid());
                secondLevelLabel.setParentId(label2.getParentid());
                secondLevelLabel.setLabelLevel(label2.getLabellevel());
                secondLevelLabel.setLabelName(label2.getLabelname());

                queryWrapper.clear();
                queryWrapper.eq("isDeleted",0)
                        .eq("labelLevel",3)
                        .eq("parentId",label2.getLabelid())
                        .orderByDesc("labelRank")
                        .orderByAsc("labelID");
                List<LabelNew> result3 = labelNewService.list(queryWrapper);
                ArrayList<ThirdLevelLabel> thirdLevelLabels = new ArrayList<ThirdLevelLabel>();
                for (LabelNew label3 : result3) {
                    ThirdLevelLabel thirdLevelLabel = new ThirdLevelLabel();
                    thirdLevelLabel.setLabelId(label3.getLabelid());
                    thirdLevelLabel.setLabelLevel(label3.getLabellevel());
                    thirdLevelLabel.setLabelIcon(label3.getLabelicon());
                    thirdLevelLabel.setLabelName(label3.getLabelname());
                    thirdLevelLabels.add(thirdLevelLabel);

                }

                secondLevelLabel.setThirdLevelLabelVOS(thirdLevelLabels);
                secondLevelLabels.add(secondLevelLabel);
            }
            firstLabel.setSecondLevelLabelVOS(secondLevelLabels);
            firstLabels.add(firstLabel);
        }
        return ResultGenerator.genSuccessResultData(firstLabels);

    }
}
