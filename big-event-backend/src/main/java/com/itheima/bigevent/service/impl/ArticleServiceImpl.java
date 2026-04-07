package com.itheima.bigevent.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.bigevent.mapper.ArticleMapper;
import com.itheima.bigevent.pojo.Article;
import com.itheima.bigevent.pojo.PageBean;
import com.itheima.bigevent.service.ArticleService;
import com.itheima.bigevent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserService userService;

    @Override
    public void add(final Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        com.itheima.bigevent.pojo.User currentUser = userService.findByUsername(username);
        article.setCreateUser(currentUser.getId());
        articleMapper.insert(article);
    }

    @Override
    public PageBean<Article> list(final Integer pageNum, final Integer pageSize, final Integer categoryId, final String state) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        com.itheima.bigevent.pojo.User currentUser = userService.findByUsername(username);
        final Integer id = currentUser.getId();
        
        // 创建分页对象
        Page<Article> page = new Page<>(pageNum, pageSize);
        
        // 执行分页查询
        IPage<Article> articlePage = articleMapper.selectArticlePage(page, id, categoryId, state);
        
        // 封装结果
        PageBean<Article> pageBean = new PageBean<>();
        pageBean.setTotal(articlePage.getTotal());
        pageBean.setItems(articlePage.getRecords());
        return pageBean;
    }


    @Override
    public Article findById(final Integer id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void update(final Article article) {
        articleMapper.updateById(article);
    }

    @Override
    public void delete(final Integer id) {
        articleMapper.deleteById(id);
    }
}
