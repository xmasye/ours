package com.xmasye.ours.dao.mybatis;

import com.xmasye.ours.dao.po.CardRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CardRecordDao {

    /**
     * 查用户某些打卡任务在某个时间段里的打卡记录
     *
     * @param openid
     * @param taskIds
     * @param startTime
     * @param endTime
     * @return
     */
    List<CardRecord> selectByOpenidAndTime(@Param("openid") String openid, @Param("taskIds") List<Integer> taskIds,
                                           @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    /**
     * 插入或者更新一条打卡记录
     *
     * @param openid
     * @param taskId
     * @param day
     */
    void insertOrUpdateCardRecord(@Param("openid")String openid, @Param("taskId")int taskId, @Param("day")Date day,
                                  @Param("schedule")int schedule);

}
