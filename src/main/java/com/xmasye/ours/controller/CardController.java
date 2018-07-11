package com.xmasye.ours.controller;

import com.xmasye.ours.constance.ApiResult;
import com.xmasye.ours.constance.ErrorCode;
import com.xmasye.ours.context.UserContext;
import com.xmasye.ours.service.CardRecordService;
import com.xmasye.ours.service.CardService;
import com.xmasye.ours.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRecordService cardRecordService;

    /**
     * 每天的打卡信息，打卡任务+打卡记录，按打卡任务配置的顺序排序
     *
     * http://localhost:8080/card/info?openid=yezw&startTime=1519833600000&endTime=1521216000000
     *
     * @param startTime 开始时间，0点的时间，单位:ms
     * @param endTime   结束时间，0点的时间，单位:ms
     * @return
     */
    @RequestMapping("/info")
    public ApiResult cardInfo(long startTime, long endTime) {
        if(startTime<=0 || endTime<=0 || startTime>endTime){
            return ApiResult.build(ErrorCode.PARAMETER_ERROR);
        }
        return ApiResult.buildSuccess(cardService.cardInfo(startTime, endTime));
    }

    /**
     * 更新打卡进度
     *
     * http://localhost:8080/card/record/mod?openid=yezw&taskId=1&day=1519833600000&schedule=34
     *
     * @param day       给这天更新打卡进度，0点的时间，单位:ms
     * @param params    批量更新打卡任务进度
     * @return
     */
    @RequestMapping("/record/mod")
    public ApiResult modCardRecord(long day, String params) {
        if(day<=0 || StringUtils.isEmpty(params)){
            return ApiResult.build(ErrorCode.PARAMETER_ERROR);
        }
        HashMap<String, Integer> paramMap = JsonUtil.parseObject(params, new HashMap<String, Integer>().getClass());
        if(CollectionUtils.isEmpty(paramMap)){
            return ApiResult.build(ErrorCode.FAILURE);
        }

        Date date = new Date(day);
        for (String taskId : paramMap.keySet()) {
            cardRecordService.modCardRecord(Integer.valueOf(taskId), date, paramMap.get(taskId));
        }

        return ApiResult.buildSuccess();
    }

}
