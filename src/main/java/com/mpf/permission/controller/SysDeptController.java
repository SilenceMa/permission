package com.mpf.permission.controller;

import com.mpf.permission.common.JsonData;
import com.mpf.permission.dto.DeptLevelDto;
import com.mpf.permission.param.DeptParam;
import com.mpf.permission.service.SysDeptService;
import com.mpf.permission.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping(value = "/dept.json")
    public JsonData saveDept(DeptParam param){
        deptService.save(param);
        return JsonData.success();
    }

    @GetMapping(value = "/tree.json")
    public JsonData tree(){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }
}
