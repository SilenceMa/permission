package com.mpf.permission.service;

import com.mpf.permission.bean.SysUser;
import com.mpf.permission.dao.SysUserMapper;
import com.mpf.permission.exception.ParamException;
import com.mpf.permission.param.UserParam;
import com.mpf.permission.util.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(UserParam param){
        BeanValidator.check(param);

        if(checkTelephoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("电话已被占用");
        }

        if (checkEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }

        String password = "123456";
        SysUser user = SysUser.builder().username(param.getUsername()).build();

    }

    private boolean checkEmailExist(String mail,Integer userId){
        return false;
    }

    private boolean checkTelephoneExist(String telephone,Integer userId){
        return false;
    }
}
