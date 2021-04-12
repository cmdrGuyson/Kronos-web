package com.guyson.kronos.interceptor;

import com.guyson.kronos.util.ExtraUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestLoggerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RestLoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestIP = ExtraUtilities.getRemoteAddr(request);

        String statement = String.format("[HANDLING REST REQUEST] [%s] [%s] [%s]", request.getRequestURI(), request.getMethod(), requestIP);

        log.info(statement);

        return true;
    }


}
