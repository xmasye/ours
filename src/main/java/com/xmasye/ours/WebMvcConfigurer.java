package com.xmasye.ours;

import com.google.common.collect.Lists;
import com.xmasye.ours.interceptor.AccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    //增加拦截器
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AccessInterceptor())    //指定拦截器类
                .addPathPatterns("/**")        //指定该类拦截的url
                .excludePathPatterns(Lists.newArrayList("/user/login"))  // 排除掉的url
        ;
    }

}
