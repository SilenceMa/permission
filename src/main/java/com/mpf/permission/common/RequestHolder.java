package com.mpf.permission.common;

import com.mpf.permission.bean.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 相当于一个map map中的key是当前的进程，有效的在多个进程之间分离，数据不会冲突
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    public static void add(SysUser user){

        USER_THREAD_LOCAL.set(user);
    }

    public static SysUser getCurrentUser(){
        return USER_THREAD_LOCAL.get();
    }

    public static void removeUserLocal(){
        USER_THREAD_LOCAL.remove();
    }


    public static void add(HttpServletRequest request){
        REQUEST_THREAD_LOCAL.set(request);
    }

    public static void removeRequestLocal(){
        REQUEST_THREAD_LOCAL.remove();
    }

    public static HttpServletRequest getCurrentRequest(){
        return REQUEST_THREAD_LOCAL.get();
    }

    /*如果不对数据移除，会造成内存泄漏*/
    public static void remove(){
        REQUEST_THREAD_LOCAL.remove();
        USER_THREAD_LOCAL.remove();
    }


}
