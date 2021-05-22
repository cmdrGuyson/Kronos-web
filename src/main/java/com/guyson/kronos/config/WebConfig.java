package com.guyson.kronos.config;

import com.guyson.kronos.interceptor.RestLoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* Only intercepts RESTful API requests */
        registry.addInterceptor(new RestLoggerInterceptor()).addPathPatterns("/api/**");
    }
}
