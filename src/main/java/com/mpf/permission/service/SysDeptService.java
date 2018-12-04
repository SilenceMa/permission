package com.mpf.permission.service;

import com.google.common.base.Preconditions;
import com.mpf.permission.bean.SysDept;
import com.mpf.permission.dao.SysDeptMapper;
import com.mpf.permission.exception.ParamException;
import com.mpf.permission.param.DeptParam;
import com.mpf.permission.util.BeanValidator;
import com.mpf.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.PAData;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    /*保存部门*/
    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept sysDept = SysDept.builder().seq(param.getSeq()).name(param.getName()).remark(param.getRemark()).parentId(param.getParentId()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperator("system");
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);

    }

    /*更新部门*/
    @Transactional
    public void update(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(),param.getName(),param.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");

        SysDept after = SysDept.builder().id(param.getId()).seq(param.getSeq()).name(param.getName()).remark(param.getRemark()).parentId(param.getParentId()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperateIp("127.0.0.1");
        after.setOperator("system");
        after.setOperateTime(new Date());
        updateWithChild(before,after);
    }

    /*更新子部门*/
    private void updateWithChild(SysDept before,SysDept after){


        /*获取当前部门信息*/
        String newLevel = after.getLevel();

        /*获取原来的部门level*/
        String oldLevel = before.getLevel();

        if (newLevel.equals(oldLevel)){
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(oldLevel);
            if (CollectionUtils.isNotEmpty(deptList)){
                for (SysDept sysDept : deptList){
                    String level = sysDept.getLevel();

                    if (level.indexOf(oldLevel) == 0){
                        level = newLevel + level.substring(oldLevel.length());
                        sysDept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }

        /*更新当前部门信息*/
        sysDeptMapper.updateByPrimaryKey(after);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {

        int count = sysDeptMapper.countByNameAndParentId(parentId,deptName,deptId);

        if (count > 0){
            return true;
        }
        return false;
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
