package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Utils.MD5;
import com.root.Utils.R;
import com.root.pojo.Student;
import com.root.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class studentController {

    @Autowired
    private StudentService studentService;

    //根据条件查询用户
    @GetMapping("/getStudentByOpr/{page}/{pageSize}")
    public R<Page<Student>> getStudentPageInfo(@PathVariable("page") Integer pages
                                                , @PathVariable("pageSize") Integer pageSize,
                                               String name, String clazzName)
    {
        Page<Student> page = new Page<>(pages, pageSize);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>()
                .eq(name != null, Student::getName, name)
                .eq(clazzName != null,Student::getClazzName,clazzName);
        studentService.page(page,wrapper);
        return R.ok(page);
    }

    //更新或者保存当前学生的信息
    @PostMapping("/addOrUpdateStudent")
    public R addStudentInfo(@RequestBody Student student)
    {
        if(student == null)
        {
            R.fail().message("学生信息不完整 请检查后再提交!!!");
        }
        student.setPassword(MD5.encrypt(student.getPassword()));
        studentService.saveOrUpdate(student);
        return R.ok().message("信息保存成功!!!");
    }

    @DeleteMapping("/delStudentById")
    public R deleteByIds(@RequestBody List<Long> list)
    {
        studentService.removeBatchByIds(list);
        return R.ok().message("删除成功!!!");
    }
}
