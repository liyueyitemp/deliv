package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insert(DishFlavor dishFlavor);
//
//    void update(DishFlavor dishFlavor);
//
//    @Select("select * from dish_flavor where id = #{id}")
//    DishFlavor getById(Long id);

    void insertBatch(List<DishFlavor> dishFlavors);

    void deleteByDishIdBatch(List<Long> dishIds);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getFlavorByDishId(Long id);

    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);
}
