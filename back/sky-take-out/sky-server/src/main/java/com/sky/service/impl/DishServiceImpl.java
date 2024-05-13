package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //多个表格操作，保持一致性. 注意要在Application开@EnableTransactionManager
    @Transactional(rollbackFor = Exception.class)
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        //注意命名需要保持一致
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.insert(dish);

        // 用for loop调用sql效率太低
//        for (DishFlavor flavor : dishDTO.getFlavors()) {
//            flavor.setDishId(dish.getId());
//        }
        //noticed in mapper we did userKeyGenerated and key... to get the generated key
        Long dishId = dish.getId();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();

        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(dishFlavors);
        }
    }

    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(DishDTO dishDTO) {
        Long dishId = dishDTO.getId();

        if (dishId == null) {
            throw new NoSuchElementException("dish id is empty");
        }

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        dishFlavorMapper.deleteByDishId(dishId);

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();

        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });

            dishFlavorMapper.insertBatch(dishFlavors);
        }
    }

    public DishVO getById(Long id) {
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dishMapper.getById(id), dishVO);
        dishVO.setFlavors(dishFlavorMapper.getFlavorByDishId(id));
        return dishVO;
    };

    public void changeStatus(Integer status, Long id) {

        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .updateUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .build();

        dishMapper.update(dish);
    }

    public List<Dish> getByCategory(Integer categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }
//
//    public Dish getById(Long id) {
//        return dishMapper.getById(id);
//    }
//
    //考虑一下清理云端资源呢！！
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //感觉这里可以优化
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish != null && StatusConstant.ENABLE.equals(dish.getStatus())) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        List<Long>setmealIds = setmealDishMapper.getByDishIds(ids);

        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deleteBatch(ids);

        dishFlavorMapper.deleteByDishIdBatch(ids);


    }

}
