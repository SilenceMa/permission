package com.mpf.permission.controller;

import com.mpf.permission.common.JsonData;
import com.mpf.permission.dto.DeptLevelDto;
import com.mpf.permission.param.DeptParam;
import com.mpf.permission.service.SysDeptService;
import com.mpf.permission.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping(value = "/save.json")
    public JsonData saveDept(DeptParam param)
    {
        deptService.save(param);
        return JsonData.success();
    }

    @GetMapping(value = "/tree.json")
    public JsonData tree()
    {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    @PostMapping("/update.json")
    public JsonData updateDept(DeptParam param)
    {
        deptService.update(param);
        return JsonData.success();
    }

}
