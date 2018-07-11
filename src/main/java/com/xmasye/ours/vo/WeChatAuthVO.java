package com.xmasye.ours.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeChatAuthVO {

    private String openid;
    private String accessKey;

    public WeChatAuthVO() {
    }

    public WeChatAuthVO(String openid, String accessKey) {
        this.openid = openid;
        this.accessKey = accessKey;
    }
}
