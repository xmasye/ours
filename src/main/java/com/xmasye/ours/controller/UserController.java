package com.xmasye.ours.controller;

import com.google.common.collect.Maps;
import com.xmasye.ours.constance.ApiResult;
import com.xmasye.ours.constance.ErrorCode;
import com.xmasye.ours.service.AccessTokenService;
import com.xmasye.ours.service.WeChatService;
import com.xmasye.ours.util.MD5Util;
import com.xmasye.ours.vo.WeChatAuthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WeChatService weChatService;

    @Autowired
    AccessTokenService accessTokenService;

    @RequestMapping("/login")
    public ApiResult login(String code) {
        if(StringUtils.isEmpty(code)){
            return ApiResult.build(ErrorCode.PARAMETER_ERROR);
        }

        WeChatAuthVO weChatAuthVO = weChatService.login(code);
        if(weChatAuthVO == null){
            return ApiResult.build(ErrorCode.FAILURE);
        }

        String accessToken= MD5Util.md5(UUID.randomUUID().toString().replaceAll("-", ""));
        //缓存用户信息数据
        accessTokenService.add(accessToken, weChatAuthVO.getOpenid());

        logger.info("登录用户的openid={}", weChatAuthVO.getOpenid());

        Map<String, String> data = Maps.newHashMap();
        data.put("accessToken", accessToken);
        return ApiResult.buildSuccess(data);
    }

}
