package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getByCategory(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
//
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    void deleteBatch(List<Long> ids);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getByCategoryId(Integer categoryId);

    @Select("select id from dish")
    List<Long> getAllDishId();
}
