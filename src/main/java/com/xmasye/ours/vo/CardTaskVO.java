package com.xmasye.ours.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 打卡任务
 */
@Getter
@Setter
public class CardTaskVO {

    private int id;
    private String taskName;
    private String color;
    @JsonIgnore
    private Date startTime;
    @JsonIgnore
    private Date endTime;

}
