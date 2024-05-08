package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

public interface CategoryService {
    
    void add(CategoryDTO categoryDTO);

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(CategoryDTO categoryDTO);

    void changeStatus(Integer status, Long id);

    Category getById(Long id);

    void deleteById(Long id);
}
