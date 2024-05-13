package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
//接口文档生成
@Api(tags = "dish related controller")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 增加分类
     * @param dishDTO the dish that needs to be added
     * @return whether the add the successful
     */

    @PostMapping()
    @ApiOperation(value = "add dish")
    public Result<String> add(@RequestBody DishDTO dishDTO) {

        log.info("new dish ... {}", dishDTO);
        dishService.add(dishDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param dishPageQueryDTO the dish that needs to be added
     * @return whether the add the successful
     */

    @GetMapping("/page")
    @ApiOperation(value = "return dish page")
    // the input is not a js object ...
    public Result<PageResult> page (DishPageQueryDTO dishPageQueryDTO) {

        log.info("page request {}", dishPageQueryDTO);
        return Result.success(dishService.page(dishPageQueryDTO));

    }

    /**
     * 更改分类状态
     * @param status the result status of dish
     * @param id the dish that needs to be adjusted
     * @return whether the change status the successful
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "status")
    public Result<String> changeStatus(@PathVariable("status") Integer status, Long id) {

        log.info("change status for dish {} to status {}", id, status);
        dishService.changeStatus(status, id);
        return Result.success();

    }

    /**
     * 更新分类信息
     * @param dishDTO the dish that needs to be added
     * @return whether the add the successful
     */
    @PutMapping()
    @ApiOperation(value = "edit dish")
    public Result<String> update(@RequestBody DishDTO dishDTO) {

        log.info("update dish ... {}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get dish by id")
    public Result<DishVO> getDishById(@PathVariable("id") Long id) {

        log.info("Looking up dish with id {}", id);
        DishVO dish = dishService.getById(id);
        return Result.success(dish);

    }

    /**
     * 分类详情
     * @param categoryId the category that needs to be checked
     * @return detail info about dish
     */

    @GetMapping("/list")
    @ApiOperation(value = "type lookup")

    public Result<List<Dish>> lookup (Integer categoryId) {

        log.info("Looking up dish with categoryId {}", categoryId);
        List<Dish> dish = dishService.getByCategory(categoryId);
        return Result.success(dish);

    }

    /**
     * 单个分类删除
     * @param ids the dishes that needs to be deleted
     * @return whether delete was successful
     */

    @DeleteMapping()
    @ApiOperation(value = "batch delete")

    public Result<String> delete (@RequestParam("ids") List<Long> ids) {

        log.info("Deleting dish with id {}", ids);
        dishService.deleteBatch(ids);

        return Result.success();

    }


}
