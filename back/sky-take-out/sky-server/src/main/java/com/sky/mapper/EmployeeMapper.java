package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username the unique username that identify employee
     * @return the detailed information on this user, if there is any
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * add employee
     * @param employee add employee to the employee chart
     */
    void insert(Employee employee);

}
