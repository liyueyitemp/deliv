package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
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
@RequestMapping("/admin/setmeal")
@Slf4j
//接口文档生成
@Api(tags = "setmeal related controller")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 增加分类
     * @param setmealDTO the setmeal that needs to be added
     * @return whether the add the successful
     */

    @PostMapping()
    @ApiOperation(value = "add setmeal")
    public Result<String> add(@RequestBody SetmealDTO setmealDTO) {

        log.info("new setmeal ... {}", setmealDTO);
        setmealService.add(setmealDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param setmealPageQueryDTO the setmeal that needs to be added
     * @return whether the add the successful
     */

    @GetMapping("/page")
    @ApiOperation(value = "return setmeal page")
    // the input is not a js object ...
    public Result<PageResult> page (SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("page request {}", setmealPageQueryDTO);
        return Result.success(setmealService.page(setmealPageQueryDTO));

    }
//
//    /**
//     * 更改分类状态
//     * @param status the result status of setmeal
//     * @param id the setmeal that needs to be adjusted
//     * @return whether the change status the successful
//     */
//    @PostMapping("/status/{status}")
//    @ApiOperation(value = "status")
//    public Result<String> changeStatus(@PathVariable("status") Integer status, Long id) {
//
//        log.info("change status for setmeal {} to status {}", id, status);
//        setmealService.changeStatus(status, id);
//        return Result.success();
//
//    }
//
    /**
     * 更新分类信息
     * @param setmealDTO the setmeal that needs to be added
     * @return whether the add the successful
     */
    @PutMapping()
    @ApiOperation(value = "edit setmeal")
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {

        log.info("update setmeal ... {}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get setmeal by id")
    public Result<SetmealVO> getSetmealById(@PathVariable("id") Long id) {

        log.info("Looking up setmeal with id {}", id);
        SetmealVO setmeal = setmealService.getById(id);
        return Result.success(setmeal);

    }

//    /**
//     * 分类详情
//     * @param type the setmeal that needs to be returned
//     * @return detail info about setmeal
//     */
//
//    @GetMapping("/list")
//    @ApiOperation(value = "type lookup")
//
//    public Result<List<Setmeal>> lookup (Integer categoryId) {
//
//        log.info("Looking up setmeal with categoryId {}", categoryId);
//        List<Setmeal> setmeal = setmealService.getByCategory(categoryId);
//        return Result.success(setmeal);
//
//    }
//
//    /**
//     * 单个分类删除
//     * @param ids the setmeales that needs to be deleted
//     * @return whether delete was successful
//     */
//
//    @DeleteMapping()
//    @ApiOperation(value = "batch delete")
//
//    public Result<String> delete (@RequestParam("ids") List<Long> ids) {
//
//        log.info("Deleting setmeal with id {}", ids);
//        setmealService.deleteBatch(ids);
//
//        return Result.success();
//
//    }


}
