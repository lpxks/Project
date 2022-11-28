package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Common.R;
import com.root.pojo.Employee;
import com.root.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public R<String> addEmployee(@RequestBody Employee employee, HttpServletRequest request)
    {

        //MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //创建时间
        //employee.setCreateTime(new Date());
        //创建时的管理员id
        //employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        //employee.setUpdateTime(new Date());
        employee.setStatus(1);
        //employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        R<String> success = R.success("添加成功!!!");
        success.setCode(1);
        return success;
    }


    //把要去的页码 以及每页展示的数量参数传递过来
    @GetMapping("/employee/page")
    public R<Page<Employee>> getAllEmployee(@RequestParam("pageSize") Integer pageSize,
                                            @RequestParam("page") Integer page,
                                            @RequestParam(value = "name",required = false)String name)
    {
        //未带姓名的普通查询
//        if(name == null) {
//            Page<Employee> pages = employeeService.getEmployees(pageSize, page);
//            R<Page<Employee>> info = R.success(pages);
//            info.setCode(1);
//            return info;
//        }
//        //带搜索姓名的分页查询
//        R<Page<Employee>> pageName = R.success(employeeService.getEmployeeByName(pageSize, page,name));
//        pageName.setCode(1);
//        return pageName;
        Page<Employee> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null,Employee::getName,name);
        wrapper.orderByAsc(Employee::getUpdateTime);
        return R.success(employeeService.page(pageInfo,wrapper));
    }

    //获取员工信息展示在表中
    @GetMapping("/employee/{id}")
    public R<Employee> getEmployeeById(@PathVariable("id")Long id)
    {
        log.info("id值为:{}",id);
        return R.success(employeeService.getOne(new QueryWrapper<Employee>().eq("id", id)));
    }

    //修改员工信息
    @PutMapping("/employee")
    public R<String> updateEmployeeInfo(HttpServletRequest request,@RequestBody Employee employee)
    {
//        log.info("当前员工的信息为{}",employee.toString());
        //更新是哪一位员工修改的
        //employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        //employee.setUpdateTime(new Date());//修改更新的时间
        boolean update = employeeService.update(employee, new QueryWrapper<Employee>().eq("id",employee.getId()));
//        log.info("修改结果为:{}",update);
        return R.success("修改成功");
    }

}
