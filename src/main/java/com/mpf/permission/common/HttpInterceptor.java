package com.mpf.permission.common;

import com.mpf.permission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Map;

/**
 * 拦截所有请求
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info("request start url is {},params is {}",requestURI,JsonMapper.obj2String(parameterMap));
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME,startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info("request finished url is {},params is {}",requestURI,JsonMapper.obj2String(parameterMap));

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info("request completion url is {},params is {}",requestURI,JsonMapper.obj2String(parameterMap));

        long endTime = System.currentTimeMillis();
        long startTime  = (long) request.getAttribute(START_TIME);

        log.info("request completed cost {} ms",endTime-startTime);
    }
}
