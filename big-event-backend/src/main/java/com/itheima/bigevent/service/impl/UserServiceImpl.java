package com.itheima.bigevent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.bigevent.mapper.UserMapper;
import com.itheima.bigevent.pojo.User;
import com.itheima.bigevent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(final String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void register(final String username, final String password) {
        // 使用 BCrypt 加密密码
        final String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        userMapper.insert(user);
    }

    @Override
    public void update(final User user) {
        userMapper.updateById(user);
    }

    @Override
    public void updateAvatar(final String url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = findByUsername(username);
        
        User user = new User();
        user.setId(currentUser.getId());
        user.setUserPic(url);
        userMapper.updateById(user);
    }

    @Override
    public void updatePwd(final String newPwd) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = findByUsername(username);
        
        // 使用 BCrypt 加密新密码
        final String encodedPassword = passwordEncoder.encode(newPwd);
        User user = new User();
        user.setId(currentUser.getId());
        user.setPassword(encodedPassword);
        userMapper.updateById(user);
    }
}
