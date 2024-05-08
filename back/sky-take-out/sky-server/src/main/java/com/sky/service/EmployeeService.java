package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO the user that is trying to log-in
     * @return employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void add(EmployeeDTO employeeDTO);

    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    void update(EmployeeDTO employeeDTO);

    void changeStatus(Integer status, Long id);

    Employee getById(Long id);

    void editPassword(PasswordEditDTO passwordEditDTO);
}
