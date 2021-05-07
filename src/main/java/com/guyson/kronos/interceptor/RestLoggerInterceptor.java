package com.guyson.kronos.interceptor;

import com.guyson.kronos.exception.UnauthorizedException;
import com.guyson.kronos.util.ExtraUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RestLoggerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RestLoggerInterceptor.class);

    private List<String> blacklistedIps = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //Add demo ip to blacklisted ips
        blacklistedIps.add("175.157.122.164");

        String requestIP = ExtraUtilities.getRemoteAddr(request);

        if(blacklistedIps.contains(requestIP)) {

            String statement = String.format("[PREVENTING REST REQUEST FROM BLACKLISTED IP] [%s] [%s] [%s]", request.getRequestURI(), request.getMethod(), requestIP);

            log.info(statement);

            throw new UnauthorizedException("Blacklisted IP");
        }

        String statement = String.format("[HANDLING REST REQUEST] [%s] [%s] [%s]", request.getRequestURI(), request.getMethod(), requestIP);

        log.info(statement);

        return true;
    }

}
