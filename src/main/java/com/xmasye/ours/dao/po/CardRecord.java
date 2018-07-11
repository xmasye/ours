package com.xmasye.ours.dao.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CardRecord {

    private int id;
    private String openid;
    private int taskId;
    private Date day;
    private int schedule;
    private Date createTime;

}
