package com.mpf.permission.param;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class TestVo {
    @NotBlank
    private String msg;

    @NotNull(message = "id 不能为空")
    @Max(value = 10,message = "id 不能大于10")
    @Min(value = 0,message = "id 至少大于0")
    private Integer id;

    @NotEmpty
    private List<String> list;
}
