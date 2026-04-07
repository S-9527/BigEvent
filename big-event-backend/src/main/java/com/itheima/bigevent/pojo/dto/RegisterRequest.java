package com.itheima.bigevent.pojo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @Pattern(regexp = "^\\S{5,16}$", message = "用户名格式不正确")
    private String username;
    
    @Pattern(regexp = "^\\S{5,16}$", message = "密码格式不正确")
    private String password;
    
    @Pattern(regexp = "^\\S{5,16}$", message = "确认密码格式不正确")
    private String rePassword;
}
