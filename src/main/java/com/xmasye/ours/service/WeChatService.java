package com.xmasye.ours.service;

import com.xmasye.ours.util.HttpUtil;
import com.xmasye.ours.util.JsonUtil;
import com.xmasye.ours.util.ResponseUtil;
import com.xmasye.ours.vo.WeChatAuthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.json.JsonObject;

@Service
public class WeChatService {

    private Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    private static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    private static final String APP_ID = "wxa2e0f5760dda5a87";
    private static final String APP_SECRET = "ee48e3c0171c828a54c2b641c994b201";

    /**
     * 用户登录时，code换取用户信息
     *
     * @param code
     * @return
     */
    public WeChatAuthVO login(String code) {
        if(StringUtils.isEmpty(code)){
            return null;
        }

        String url = String.format(AUTH_URL, APP_ID, APP_SECRET, code);
        String responseText = HttpUtil.get(url);
        logger.info("[通知小程序]用户登录 responseText={}", responseText);

        if (StringUtils.isEmpty(responseText)) {
            //系统繁忙 请稍候重试
            logger.warn("[通知小程序]登录失败");
            return null;
        }

        // 解析返回字段
        JsonObject jsonObject = null;
        try {
            jsonObject = JsonUtil.toJsonObject(responseText);
            if (jsonObject==null || jsonObject.containsKey("errcode") ||
                    StringUtils.isEmpty(jsonObject.getString("openid")) ||
                    StringUtils.isEmpty(jsonObject.getString("session_key"))) {
                //微信接口调用失败
                logger.warn("[通知小程序]登录失败");
                return null;
            }
        } catch (Exception ex){
            logger.error("[通知小程序]登录失败", ex);
            return null;
        }

        return new WeChatAuthVO(jsonObject.getString("openid"), jsonObject.getString("session_key"));
    }

}
