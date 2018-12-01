package com.mpf.permission.dto;

import com.google.common.collect.Lists;
import com.mpf.permission.bean.SysDept;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept) {
        DeptLevelDto dto = new DeptLevelDto();

        BeanUtils.copyProperties(dept, dto);

        return dto;
    }
}
