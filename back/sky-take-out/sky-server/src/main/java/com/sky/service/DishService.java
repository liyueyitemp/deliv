package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

public interface DishService {


    void add(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void update(DishDTO dishDTO);

    void changeStatus(Integer status, Long id);

    Dish getById(Long id);
}
