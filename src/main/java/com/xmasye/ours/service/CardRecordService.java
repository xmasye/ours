package com.xmasye.ours.service;

import com.google.common.collect.Lists;
import com.xmasye.ours.context.UserContext;
import com.xmasye.ours.dao.mybatis.CardRecordDao;
import com.xmasye.ours.util.VOUtil;
import com.xmasye.ours.vo.CardRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class CardRecordService {

    @Autowired
    private CardRecordDao cardRecordDao;

    /**
     * 查用户某些打卡任务在某个时间段里的打卡记录
     *
     * @param openid
     * @param taskIds
     * @param startTime
     * @param endTime
     * @return
     */
    public List<CardRecordVO> findByOpenidAndTime(String openid, List<Integer> taskIds, Date startTime, Date endTime) {
        if(StringUtils.isEmpty(openid) || CollectionUtils.isEmpty(taskIds)){
            return Lists.newArrayList();
        }
        return VOUtil.fromList(cardRecordDao.selectByOpenidAndTime(openid, taskIds, startTime, endTime), CardRecordVO.class);
    }

    /**
     * 更新打卡进度
     *
     * @param taskId
     * @param day
     */
    public void modCardRecord(int taskId, Date day, int schedule){
        if(taskId<=0 || day==null || schedule<0){
            return;
        }
        String openid = UserContext.getOpenid();
        cardRecordDao.insertOrUpdateCardRecord(openid, taskId, day, schedule);
    }

}
