package com.xmasye.ours.dao.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CardTask {

    private int id;
    private String openid;
    private String taskName;
    private String color;
    private int rank;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private int taskStatus;

}
