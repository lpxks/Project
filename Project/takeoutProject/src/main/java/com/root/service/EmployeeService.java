package com.root.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liuzexiong
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2022-09-23 17:22:29
*/
public interface EmployeeService extends IService<Employee> {

    public Page<Employee> getEmployees(Integer pageSize, Integer size);

    public Page<Employee> getEmployeeByName(Integer pageSize, Integer size,String name);
}
