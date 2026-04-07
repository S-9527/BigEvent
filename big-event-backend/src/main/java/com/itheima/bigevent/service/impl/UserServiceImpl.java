package com.itheima.bigevent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.bigevent.mapper.UserMapper;
import com.itheima.bigevent.pojo.User;
import com.itheima.bigevent.service.UserService;
import com.itheima.bigevent.utils.Md5Util;
import com.itheima.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(final String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void register(final String username, final String password) {
        final String md5String = Md5Util.getMD5String(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5String);
        userMapper.insert(user);
    }

    @Override
    public void update(final User user) {
        userMapper.updateById(user);
    }

    @Override
    public void updateAvatar(final String url) {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        User user = new User();
        user.setId(id);
        user.setUserPic(url);
        userMapper.updateById(user);
    }

    @Override
    public void updatePwd(final String newPwd) {
        final String md5String = Md5Util.getMD5String(newPwd);
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        User user = new User();
        user.setId(id);
        user.setPassword(md5String);
        userMapper.updateById(user);
    }
}
