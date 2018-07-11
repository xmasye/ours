package com.xmasye.ours.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class AccessTokenService {

    // 先放内存，以后可以放到redis
    private static Map<String, String> token2Openid = Maps.newHashMap();

    public void add(String accessToken, String openid){
        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openid)){
            return;
        }
        token2Openid.put(accessToken, openid);
    }

    public String getOpenid(String accessToken){
        if(StringUtils.isEmpty(accessToken)){
            return null;
        }
        return token2Openid.get(accessToken);
    }

}
