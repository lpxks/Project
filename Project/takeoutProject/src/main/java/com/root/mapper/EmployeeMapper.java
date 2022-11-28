package com.root.mapper;

import com.root.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author liuzexiong
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-09-23 17:22:29
* @Entity com.root.pojo.Employee
*/
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

}




