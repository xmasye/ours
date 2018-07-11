package com.xmasye.ours.service;

import com.google.common.collect.Lists;
import com.xmasye.ours.context.UserContext;
import com.xmasye.ours.vo.CardDailyInfoVO;
import com.xmasye.ours.vo.CardRecordVO;
import com.xmasye.ours.vo.CardTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardTaskService cardTaskService;

    @Autowired
    private CardRecordService cardRecordService;

    private static final int ONE_DAY = 60*60*24*1000;

    /**
     * 构造开始时间到结束时间之间每天的打卡信息，打卡任务+打卡记录，按打卡任务配置的顺序排序
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<Long, CardDailyInfoVO> cardInfo(long startTime, long endTime){
        Map<Long, CardDailyInfoVO> dailyInfoMap = new HashMap<>();
        if(startTime<=0 || endTime<=0 || startTime>endTime){
            return dailyInfoMap;
        }

        String openid = UserContext.getOpenid();

        // 先查任务
        List<CardTaskVO> tasks = cardTaskService.findAllTaskByOpenId(openid);
        if(CollectionUtils.isEmpty(tasks)){
            return dailyInfoMap;
        }

        // 查任务对应的每日进度
        List<Integer> taskIds = tasks.stream().map(CardTaskVO::getId).collect(Collectors.toList());
        List<CardRecordVO> records = cardRecordService.findByOpenidAndTime(openid, taskIds, new Date(startTime), new Date(endTime));
        // 每天对应的打卡记录，Key的单位是毫秒
        Map<Long, List<CardRecordVO>> dayRecordMap = records.stream().collect(Collectors.groupingBy(p->p.getDay().getTime()));

        for(long day = startTime; day<=endTime; day+=ONE_DAY){
            dailyInfoMap.put(day, buildCardDailyInfo(tasks, dayRecordMap, day));
        }

        return dailyInfoMap;
    }

    /**
     * 构造CardDailyInfoVO
     *
     * @param tasks
     * @param dayRecordMap
     * @param day
     * @return
     */
    private CardDailyInfoVO buildCardDailyInfo(List<CardTaskVO> tasks, Map<Long, List<CardRecordVO>> dayRecordMap, long day){
        CardDailyInfoVO dailyInfoVO = new CardDailyInfoVO(Lists.newArrayList(), Lists.newArrayList());

        // 当天对应的打卡记录
        List<CardRecordVO> recordVOS = dayRecordMap.get(day);

        for (CardTaskVO task : tasks) {
            // 任务当天有效
            if(task.getStartTime().getTime()<=day && day<=task.getEndTime().getTime()){
                dailyInfoVO.getTasks().add(task);

                /**
                 * 找当天对应的打卡记录，记录也按打卡任务配置的顺序排序
                 * 没有打卡，则进度为0
                 */

                CardRecordVO cardRecordVO = null;
                if(!CollectionUtils.isEmpty(recordVOS)){
                    for (CardRecordVO recordVO : recordVOS) {
                        if(recordVO.getTaskId() == task.getId()){
                            recordVO.setTaskName(task.getTaskName());
                            cardRecordVO = recordVO;
                            break;
                        }
                    }
                }

                if(cardRecordVO == null){
                    cardRecordVO = new CardRecordVO();
                    cardRecordVO.setTaskId(task.getId());
                    cardRecordVO.setTaskName(task.getTaskName());
                    cardRecordVO.setSchedule(0);
                }

                dailyInfoVO.getRecords().add(cardRecordVO);
            }
        }

        return dailyInfoVO;
    }

}
