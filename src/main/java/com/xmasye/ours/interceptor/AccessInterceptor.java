package com.xmasye.ours.interceptor;


import com.google.common.collect.Maps;
import com.xmasye.ours.constance.ApiResult;
import com.xmasye.ours.constance.ErrorCode;
import com.xmasye.ours.context.UserContext;
import com.xmasye.ours.service.AccessTokenService;
import com.xmasye.ours.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器，检测accessToken
 */
public class AccessInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = getParam(request, "accessToken", null);// 访问凭据
        if(!StringUtils.isEmpty(accessToken)){
            if (accessTokenService == null) {//解决service为null无法注入问题
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                accessTokenService = (AccessTokenService) factory.getBean("accessTokenService");
            }

            String openid = accessTokenService.getOpenid(accessToken);
            if(!StringUtils.isEmpty(openid)){
                // 放到上下文
                UserContext.setOpenid(openid);
                return true;
            }
        }

        // 权限校验不通过，返回对应错误码
        ResponseUtil.renderResult(response, ApiResult.build(ErrorCode.ACCESS_INVALID));
        return false;
    }

    private String getParam(HttpServletRequest request, String name, String defaultValue) {
        return request.getParameter(name) != null ? request.getParameter(name) : defaultValue;
    }

}
