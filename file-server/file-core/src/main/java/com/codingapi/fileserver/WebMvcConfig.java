package com.codingapi.fileserver;

import com.codingapi.fileserver.api.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author modificial
 * @date 2018/4/20 0020
 * @company codingApi
 * @description 配置拦截器
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(authenticationInterceptor());


        addInterceptor.addPathPatterns("/file/**").addPathPatterns("/image/**");

    }

    @Bean
    public HandlerInterceptor authenticationInterceptor() {

        return new AuthenticationInterceptor();
    }


}
