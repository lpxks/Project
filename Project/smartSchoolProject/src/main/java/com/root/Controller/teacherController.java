package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Utils.MD5;
import com.root.Utils.R;
import com.root.pojo.Teacher;
import com.root.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class teacherController {

    @Autowired
    private TeacherService teacherService;

    //分页查询教师的信息
    @GetMapping("/getTeachers/{page}/{pageSize}")
    public R<Page<Teacher>> getTeacherInfo(@PathVariable("page") Integer page,
                                           @PathVariable("pageSize")Integer pageSize,
                                           String name,String clazzName)
    {
        Page<Teacher> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<Teacher>()
                .eq(name != null, Teacher::getName, name)
                .eq(clazzName != null, Teacher::getClazzName, clazzName);
        teacherService.page(pages,wrapper);
        return R.ok(pages);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public R savaOrUpdate(@RequestBody Teacher teacher)
    {
        teacherService.saveOrUpdate(teacher);
        teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        return R.ok().message("修改成功!!!");
    }

    @DeleteMapping("/deleteTeacher")
    public R deleteTeacher(@RequestBody List<Long> list)
    {
        teacherService.removeBatchByIds(list);
        return R.ok().message("删除成功!!!");
    }
}
