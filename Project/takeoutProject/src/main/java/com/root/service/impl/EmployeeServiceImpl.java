package com.root.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.Employee;
import com.root.service.EmployeeService;
import com.root.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liuzexiong
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-09-23 17:22:29
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Page<Employee> getEmployees(Integer pageSize, Integer noPage) {
        Page<Employee> page = new Page<>(noPage,pageSize);
        return employeeMapper.selectPage(page,null);
    }

    //带姓名分页查询
    @Override
    public Page<Employee> getEmployeeByName(Integer pageSize, Integer size, String name) {
        Page<Employee> page = new Page<>(size,pageSize);
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return employeeMapper.selectPage(page,wrapper);
    }
}




