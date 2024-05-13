package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;;
import com.sky.entity.Category;

import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setMealMapper;

    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        // 根据attribute name 判断进行转移
        BeanUtils.copyProperties(categoryDTO, category);


        // 增加更多属性
        category.setStatus(StatusConstant.DISABLE);

        //此时
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());

        // TODO change to created/updated user later
        long creatorId = BaseContext.getCurrentId();
//        category.setCreateUser(creatorId);
//        category.setUpdateUser(creatorId);

        categoryMapper.insert(category);
    }

    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        // naive : select * from category limit page * page_size, (page+1) * page_size

        //from pagehelper plugin based on mybatis yield a dynamic sql and change limit
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        // there is a set local page that use ThreadLocal to set a local page to add limit ...
        // write sql
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        //重新分装
        return new PageResult(page.getTotal(), page.getResult());
    }


    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    public void changeStatus(Integer status, Long id) {

/*
        traditional approach
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
*/
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .build();

        categoryMapper.update(category);
    }

    public List<Category> getByType(Integer type) {
        return categoryMapper.getByType(type);
    }

    public void deleteById(Long id) {
        if (!dishMapper.getByCategory(id).isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        if (!setMealMapper.getByCategory(id).isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    };
}
