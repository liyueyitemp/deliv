package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
//import com.sky.entity.SetmealFlavor;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
//import com.sky.mapper.SetmealFlavorMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //多个表格操作，保持一致性. 注意要在Application开@EnableTransactionManager
    @Transactional(rollbackFor = Exception.class)
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        setmealMapper.insert(setmeal);

        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

//        List<Long> dishIds = dishMapper.getAllDishId();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
//                if (!dishIds.contains(setmealDish.getDishId())) {
//                    throw new BaseException("dish: " + setmealDish.getName() + " does not exist");
//                }
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Long setmealId = setmealDTO.getId();

        if (setmealId == null) {
            throw new NoSuchElementException("setmeal id is empty");
        }

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        setmealDishMapper.deleteBySetmealId(setmealId);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealFlavor -> {
                setmealFlavor.setSetmealId(setmealId);
            });

            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmealMapper.getById(id), setmealVO);
        setmealVO.setSetmealDishes(setmealDishMapper.getBySetmealId(id));
        return setmealVO;
    };

//    public void changeStatus(Integer status, Long id) {
//
//        Setmeal setmeal = Setmeal.builder()
//                .id(id)
//                .status(status)
//                .updateUser(BaseContext.getCurrentId())
//                .updateTime(LocalDateTime.now())
//                .build();
//
//        setmealMapper.update(setmeal);
//    }
//
//    public List<Setmeal> getByCategory(Integer categoryId) {
//        return setmealMapper.getByCategoryId(categoryId);
//    }
//
//    public Setmeal getById(Long id) {
//        return setmealMapper.getById(id);
//    }
//
//    //考虑一下清理云端资源呢！！
//    @Transactional
//    public void deleteBatch(List<Long> ids) {
//        //感觉这里可以优化
//        for (Long id : ids) {
//            Setmeal setmeal = setmealMapper.getById(id);
//            if (setmeal != null && StatusConstant.ENABLE.equals(setmeal.getStatus())) {
//                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
//            }
//        }
//        List<Long>setmealIds = setmealDishMapper.getBySetmealIds(ids);
//
//        if (setmealIds != null && !setmealIds.isEmpty()) {
//            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
//        }
//
//        setmealMapper.deleteBatch(ids);
//
//        setmealFlavorMapper.deleteBySetmealIdBatch(ids);
//
//
//    }

}
