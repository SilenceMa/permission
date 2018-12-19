package com.mpf.permission.filter;

import com.mpf.permission.bean.SysUser;
import com.mpf.permission.common.RequestHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();
        SysUser user = (SysUser) request.getSession().getAttribute("user");

        if (user == null){
            String path = "/signin.jsp";
            response.sendRedirect(path);
            return;
        }else {
            RequestHolder.add(user);
            RequestHolder.add(request);
        }
        /*存储完数据之后，使用过滤链继续执行filter*/
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
