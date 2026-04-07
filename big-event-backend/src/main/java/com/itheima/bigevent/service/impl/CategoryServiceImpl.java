package com.itheima.bigevent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.bigevent.mapper.CategoryMapper;
import com.itheima.bigevent.pojo.Category;
import com.itheima.bigevent.service.CategoryService;
import com.itheima.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(final Category category) {
        // Add attributes
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        category.setCreateUser(id);
        categoryMapper.insert(category);
    }

    @Override
    public List<Category> list() {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");

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
