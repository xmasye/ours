package com.xmasye.ours.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 每天打卡信息
 */
@Getter
@Setter
public class CardDailyInfoVO {

    private List<CardTaskVO> tasks;
    private List<CardRecordVO> records;

    public CardDailyInfoVO(List<CardTaskVO> tasks, List<CardRecordVO> records) {
        this.tasks = tasks;
        this.records = records;
    }
}
