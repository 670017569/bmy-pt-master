package com.bmy.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UsernameLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:19
 **/
@Data
public class UsernameLoginDTO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;

}
