package com.mpf.permission.controller;

import com.mpf.permission.bean.SysAcl;
import com.mpf.permission.bean.SysAclModule;
import com.mpf.permission.common.ApplicationContextHelper;
import com.mpf.permission.common.JsonData;
import com.mpf.permission.dao.SysAclModuleMapper;
import com.mpf.permission.exception.ParamException;
import com.mpf.permission.param.TestVo;
import com.mpf.permission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping
    public SysAcl hello() {
        log.info("this is test program ,and say hello to java !");
        SysAcl sysAcl = new SysAcl();
        sysAcl.setId(101);
        sysAcl.setName("Silence");
        sysAcl.setUrl("https://www.baidu.com");
        sysAcl.setOperateTime(new Date());
        return sysAcl;
    }

    @GetMapping(value = "/say")
    public Map<String, String> say() {
        Map<String, String> maps = new HashMap<>();
        maps.put("word", "say hello");
        return maps;
    }

    @GetMapping(value = "/sing")
    public JsonData sing(String userId) {
        SysAcl sysAcl = new SysAcl();
        sysAcl.setId(101);
        sysAcl.setName("Silence");
        sysAcl.setUrl("https://www.baidu.com");
        sysAcl.setOperateTime(new Date());
        sysAcl.setCode(userId);
        JsonData result = JsonData.success(sysAcl, "请求数据成功");
        return result;
    }

    @PostMapping(value = "/a.json")
    public JsonData testException(String ex) {

        log.info("exception is {} ", ex);
        throw new RuntimeException(ex);

    }

    @GetMapping(value = "/validate.json")
    public JsonData testValidate(TestVo vo) throws ParamException {
        /*Map<String, String> validateObject = BeanValidator.validateObject(vo);
        if (validateObject != null && validateObject.entrySet().size() > 0) {
            for (Map.Entry<String,String> entry: validateObject.entrySet()) {
                log.info("{}-->{}",entry.getKey(),entry.getValue());
            }
        }
        for (String list: vo.getList()) {
            System.out.println(list);
        }*/
        /*BeanValidator.check(vo);
        String s = JsonMapper.obj2String(vo);
        TestVo testVo = JsonMapper.string2Obj(s, new TypeReference<TestVo>() {});*/

        SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(sysAclModule));
        return JsonData.success();
    }


}
