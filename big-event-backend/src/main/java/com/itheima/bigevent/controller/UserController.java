package com.itheima.bigevent.controller;

import com.itheima.bigevent.pojo.Result;
import com.itheima.bigevent.pojo.User;
import com.itheima.bigevent.pojo.dto.LoginRequest;
import com.itheima.bigevent.pojo.dto.RegisterRequest;
import com.itheima.bigevent.security.JwtUtil;
import com.itheima.bigevent.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Result<User> register(@RequestBody @Valid final RegisterRequest registerRequest) {
        final User existingUser = userService.findByUsername(registerRequest.getUsername());
        if (existingUser == null) {
            userService.register(registerRequest.getUsername(), registerRequest.getPassword());
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid final LoginRequest loginRequest) {
        try {
            // 使用 Spring Security 进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // 认证成功后生成 Token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = JwtUtil.generateToken(userDetails);

            // 保存 token 到 Redis
            final ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, Duration.ofHours(12));

            return Result.success(token);
        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        final User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody @Validated final User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        
        if (user.getId().equals(currentUser.getId())) {
            userService.update(user);
            return Result.success();
        } else {
            return Result.error("非本人id");
        }
    }

    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL final String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> params, 
                                     jakarta.servlet.http.HttpServletRequest request) {
        final String oldPwd = params.get("old_pwd");
        final String newPwd = params.get("new_pwd");
        final String rePwd = params.get("re_pwd");
        
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        final User user = userService.findByUsername(username);
        
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次结果不一样");
        }
        
        if (!passwordEncoder.matches(oldPwd, user.getPassword())) {
            return Result.error("密码填写不正确");
        }

        userService.updatePwd(newPwd);
        
        // 获取当前 token 并从 Redis 中删除（注销）
        String token = extractToken(request);
        if (token != null) {
            final ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.getOperations().delete(token);
        }

        return Result.success();
    }

    /**
     * 从请求头中提取 Token
     */
    private String extractToken(jakarta.servlet.http.HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasLength(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
