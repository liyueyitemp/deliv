package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = DigestUtils.md5DigestAsHex(employeeLoginDTO.getPassword().getBytes());

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
//        后期需要进行md5加密，然后再进行比对
        //password = password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            //System.out.println(password);
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (StatusConstant.DISABLE.equals(employee.getStatus())) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    public void add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // 根据attribute name 判断进行转移
        BeanUtils.copyProperties(employeeDTO, employee);

        // 增加更多属性
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);

        //此时
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // TODO change to created/updated user later
        long creatorId = BaseContext.getCurrentId();
        employee.setCreateUser(creatorId);
        employee.setUpdateUser(creatorId);

        employeeMapper.insert(employee);
    }

    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        // naive : select * from employee limit page * page_size, (page+1) * page_size

        //from pagehelper plugin based on mybatis yield a dynamic sql and change limit
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        // there is a set local page that use ThreadLocal to set a local page to add limit ...
        // write sql
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        //重新分装
        return new PageResult(page.getTotal(), page.getResult());
    }
}
