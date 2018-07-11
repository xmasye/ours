package com.xmasye.ours.dao.mybatis;

import com.xmasye.ours.dao.po.CardTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardTaskDao {

    /**
     * 查用户的所有配置的打卡任务
     *
     * @param openid
     * @return
     */
    List<CardTask> selectAllTaskByOpenId(String openid);

}
