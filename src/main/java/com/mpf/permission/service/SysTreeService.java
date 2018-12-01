package com.mpf.permission.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mpf.permission.bean.SysDept;
import com.mpf.permission.dao.SysDeptMapper;
import com.mpf.permission.dto.DeptLevelDto;
import com.mpf.permission.param.TestVo;
import com.mpf.permission.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Autowired
    private SysDeptMapper deptMapper;

    public List<DeptLevelDto> deptTree() {


        List<SysDept> deptList = deptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();

        for (SysDept dept : dtoList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            deptList.add(dto);
        }

        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (CollectionUtils.isEmpty(deptLevelDtoList)) {
            return Lists.newArrayList();
        }
        //这里的 Multimap-->Map<String,list<DeptLevelDto>>
        Multimap<String, DeptLevelDto> levelDeptmap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        // 将数据中查询粗来的部门集合转换为levelDeptmap
        for (DeptLevelDto dto : deptLevelDtoList) {
            levelDeptmap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        //对根节点的数据进行排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        //递归生成树
        transformDeptTree(rootList,LevelUtil.ROOT,levelDeptmap);

        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptmap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            //循环遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // 获取下一层级的level
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层

            // 获取下一层级的所有部门数据
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptmap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                //排序
                Collections.sort(tempDeptList,deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入下一层处理
                transformDeptTree(tempDeptList,nextLevel,levelDeptmap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
