package com.itheima.bigevent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.bigevent.mapper.CategoryMapper;
import com.itheima.bigevent.pojo.Category;
import com.itheima.bigevent.service.CategoryService;
import com.itheima.bigevent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserService userService;

    @Override
    public void add(final Category category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        com.itheima.bigevent.pojo.User currentUser = userService.findByUsername(username);
        category.setCreateUser(currentUser.getId());
        categoryMapper.insert(category);
    }

    @Override
    public List<Category> list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        com.itheima.bigevent.pojo.User currentUser = userService.findByUsername(username);
        final Integer id = currentUser.getId();

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getCreateUser, id);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public Category findById(final Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void update(final Category category) {
        categoryMapper.updateById(category);
    }

    @Override
    public void delete(final Integer id) {
        categoryMapper.deleteById(id);
    }
}
