package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {


    void add(SetmealDTO dishDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void update(SetmealDTO setmealDTO);

//    PageResult page(SetmealPageQueryDTO dishPageQueryDTO);
//
//    void update(SetmealDTO dishDTO);
//
//    void deleteBatch(List<Long> ids);
//
    SetmealVO getById(Long id);
//
//    void changeStatus(Integer status, Long id);

//    List<Setmeal> getByCategory(Integer categoryId);
}
