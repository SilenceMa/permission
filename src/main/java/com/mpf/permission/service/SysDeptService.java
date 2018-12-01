package com.mpf.permission.service;

import com.mpf.permission.bean.SysDept;
import com.mpf.permission.dao.SysDeptMapper;
import com.mpf.permission.exception.ParamException;
import com.mpf.permission.param.DeptParam;
import com.mpf.permission.util.BeanValidator;
import com.mpf.permission.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    /*保存部门*/
    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同意层级下存在相同名称的部门");
        }

        SysDept sysDept = SysDept.builder().name(param.getName()).remark(param.getRemark()).parentId(param.getParentId()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperator("system");
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);

    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return true;
    }

    private String getLevel(Integer parentId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(parentId);
        if (sysDept == null){
            return null;
        }else {
            return sysDept.getLevel();
        }
    }

}
