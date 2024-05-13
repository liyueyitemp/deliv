package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.entity.DishFlavor;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.result.PageResult;
import com.sky.service.DishFlavorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    public void add(DishFlavor dishFlavor) {

        dishFlavorMapper.insert(dishFlavor);
    }
//
//
//    public void update(DishFlavor dishFlavor) {
//
//        dishFlavorMapper.update(dishFlavor);
//    }
//
//    public DishFlavor getById(Long id) {
//        return dishFlavorMapper.getById(id);
//    }
//
//    public void deleteById(Long id) {}

}
