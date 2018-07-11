package com.xmasye.ours.service;

import com.google.common.collect.Lists;
import com.xmasye.ours.dao.mybatis.CardTaskDao;
import com.xmasye.ours.dao.po.CardTask;
import com.xmasye.ours.util.VOUtil;
import com.xmasye.ours.vo.CardTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CardTaskService {

    @Autowired
    private CardTaskDao cardTaskDao;

    /**
     * 查用户的所有配置的打卡任务
     *
     * @param openid
     * @return
     */
    List<CardTaskVO> findAllTaskByOpenId(String openid) {
        if(StringUtils.isEmpty(openid)){
            return Lists.newArrayList();
        }
        List<CardTask> tasks = cardTaskDao.selectAllTaskByOpenId(openid);
        return VOUtil.fromList(tasks, CardTaskVO.class);
    }

}
