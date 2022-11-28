package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.root.Common.R;
import com.root.pojo.Employee;
import com.root.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/employee")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request)
    {
        String username = employee.getUsername();
        Employee emp = employeeService.getOne(new QueryWrapper<Employee>().eq("username",username));
        //假设当前用户不存在 或者密码错误 或者当前用户账号处于禁止状态全部为登录失败
        if(emp == null)
        {
            return R.error("当前账号不存在!!!");
        }
        //将明文密码转换为密文
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if(!password.equals(emp.getPassword()))
            return R.error("密码错误!!!");
        //当前账号处于冻结状态 status = 0表示当前账号已冻结
        if(emp.getStatus() == 0)
            return R.error("当前账号处于冻结状态!!!");
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    //退出登录
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute("id");
        return R.success("退出成功!!!");
    }
}
