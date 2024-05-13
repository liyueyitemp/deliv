package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {


    void add(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void update(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);

    DishVO getById(Long id);

    void changeStatus(Integer status, Long id);

    List<Dish> getByCategory(Integer categoryId);
}
