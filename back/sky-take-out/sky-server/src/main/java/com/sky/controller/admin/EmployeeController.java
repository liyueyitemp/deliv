package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
//接口文档生成
@Api(tags = "employee related controller")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO the employee that tries to log-in
     * @return if successful return data. if not then no
     */
    @PostMapping("/login")
    @ApiOperation(value = "employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return whether logout is successful
     */
    @PostMapping("/logout")
    @ApiOperation(value = "employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 增加员工
     * @param employeeDTO the employee that needs to be added
     * @return whether the add the successful
     */
    //@PostMapping("/add") 从接口文档进行理解

    @PostMapping()
    @ApiOperation(value = "add employee")
    public Result<String> add(@RequestBody EmployeeDTO employeeDTO) {
        // {} 为占位符
        log.info("new employee ... {}", employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    /**
     * 员工分页
     * @param employeePageQueryDTO the employee that needs to be added
     * @return whether the add the successful
     */
    //@PostMapping("/add") 从接口文档进行理解

    @GetMapping("/page")
    @ApiOperation(value = "return employee page")
    // the input is not a js object ...
    public Result<PageResult> page (EmployeePageQueryDTO employeePageQueryDTO) {

        log.info("page request {}", employeePageQueryDTO);
        return Result.success(employeeService.page(employeePageQueryDTO));

    }

    /**
     * 更改员工状态
     * @param status the result status of employee
     * @param id the employee that needs to be adjusted
     * @return whether the change status the successful
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "status")
    // note the integer status is under the same name as PostMapping {status} so the PathVariable is not necessary
    //PathVariable("match something in {}")
    public Result<String> changeStatus(@PathVariable("status") Integer status, Long id) {
        // {} 为占位符
        log.info("change status for employee {} to status {}", id, status);

        employeeService.changeStatus(status, id);
        return Result.success();

    }

    /**
     * 更新员工信息
     * @param employeeDTO the employee that needs to be added
     * @return whether the add the successful
     */
    @PutMapping()
    @ApiOperation(value = "edit employee")
    public Result<String> update(@RequestBody EmployeeDTO employeeDTO) {
        // {} 为占位符
        log.info("update employee ... {}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

    /**
     * 员工详情
     * @param id the employee that needs to be returned
     * @return detail info about employee
     */
    //@PostMapping("/add") 从接口文档进行理解

    @GetMapping("/{id}")
    @ApiOperation(value = "single lookup")
    // the input is not a js object ...
    public Result<Employee> lookup (@PathVariable("id") Long id) {
        // the video claimed long id
        log.info("Looking up employee with id {}", id);
        //return Result.success(employeeService.getById(Long.valueOf(id)));
        Employee employee = employeeService.getById(id);
        // manually avoid passing password related parameter to front end
        employee.setPassword("****");
        return Result.success(employee);
    }

    /**
     * 更新密码
     * @param passwordEditDTO the employee that needs to be added
     * @return whether the add the successful
     */
    @PutMapping("editPassword")
    @ApiOperation(value = "reset password")
    public Result<String> editPassword(@RequestBody PasswordEditDTO passwordEditDTO) {
        // {} 为占位符
        log.info("update employee password ... {}", passwordEditDTO);
        employeeService.editPassword(passwordEditDTO);
        return Result.success();
    }






}
