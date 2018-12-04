package com.mpf.permission.param;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Length(min = 2,max = 15,message = "部门长度需要在2-15个字符之间")
    private String name;

    /*保证参数不能为空*/
    private Integer parentId = 0;

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;

    @Length(max = 150,message = "部门备注不能大于150个字符")
    private String remark;

}
