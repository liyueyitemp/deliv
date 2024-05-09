package com.sky.controller.admin;

import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;

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
@RequestMapping("/admin/category")
@Slf4j
//接口文档生成
@Api(tags = "category related controller")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 增加分类
     * @param categoryDTO the category that needs to be added
     * @return whether the add the successful
     */

    @PostMapping()
    @ApiOperation(value = "add category")
    public Result<String> add(@RequestBody CategoryDTO categoryDTO) {

        log.info("new category ... {}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO the category that needs to be added
     * @return whether the add the successful
     */

    @GetMapping("/page")
    @ApiOperation(value = "return category page")
    // the input is not a js object ...
    public Result<PageResult> page (CategoryPageQueryDTO categoryPageQueryDTO) {

        log.info("page request {}", categoryPageQueryDTO);
        return Result.success(categoryService.page(categoryPageQueryDTO));

    }

    /**
     * 更改分类状态
     * @param status the result status of category
     * @param id the category that needs to be adjusted
     * @return whether the change status the successful
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "status")
    // note the integer status is under the same name as PostMapping {status} so the PathVariable is not necessary
    //PathVariable("match something in {}")
    public Result<String> changeStatus(@PathVariable("status") Integer status, Long id) {

        log.info("change status for category {} to status {}", id, status);
        categoryService.changeStatus(status, id);
        return Result.success();

    }

    /**
     * 更新分类信息
     * @param categoryDTO the category that needs to be added
     * @return whether the add the successful
     */
    @PutMapping()
    @ApiOperation(value = "edit category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {

        log.info("update category ... {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();

    }

    /**
     * 分类详情
     * @param type the category that needs to be returned
     * @return detail info about category
     */

    @GetMapping("/list")
    @ApiOperation(value = "type lookup")

    public Result<List<Category>> lookup (Integer type) {

        log.info("Looking up category with id {}", type);
        List<Category> category = categoryService.getByType(type);
        return Result.success(category);

    }

    /**
     * 单个分类删除
     * @param id the category that needs to be deleted
     * @return whether delete was successful
     */

    @DeleteMapping()
    @ApiOperation(value = "delete by id")

    public Result<String> delete (@RequestParam("id") Long id) {

        log.info("Deleting category with id {}", id);
        categoryService.deleteById(id);
        return Result.success();

    }


}
