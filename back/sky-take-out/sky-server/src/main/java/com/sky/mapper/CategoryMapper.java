package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    /**
     * 根据用户名查询分类
     * @param name the unique username that identify category
     * @return the detailed information on this user, if there is any
     */
    @Select("select * from category where name = #{name}")
    Category getByUsername(String name);

    @Select("select * from category where id = #{id}")
    Category getById(Long id);

    /**
     * add category
     * @param category add category to the category chart
     */
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);
}
