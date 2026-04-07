package com.itheima.bigevent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.bigevent.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> selectArticlePage(Page<Article> page, @Param("id") Integer id, @Param("categoryId") Integer categoryId, @Param("state") String state);

}
