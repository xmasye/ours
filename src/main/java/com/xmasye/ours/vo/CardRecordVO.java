package com.xmasye.ours.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 打卡记录
 */
@Getter
@Setter
public class CardRecordVO {

    private int id;
    private int taskId;
    private String taskName;
    @JsonIgnore
    private Date day;
    private int schedule;

}
