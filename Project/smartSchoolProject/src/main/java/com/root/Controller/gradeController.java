package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Utils.R;
import com.root.Utils.ResultCodeEnum;
import com.root.pojo.Grade;
import com.root.service.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sms/gradeController")
public class gradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades")
    public R<List<Grade>> getAllGrade()
    {
        return R.ok(gradeService.list(null));
    }

    @GetMapping("/getGrades/{page}/{pageSize}")
    public R<Page<Grade>> getGradeInfo(@PathVariable("page")Integer pages
                                        ,@PathVariable("pageSize") Integer pageSize,
                                       String gradeName)
    {
        Page<Grade> page = new Page<>(pages,pageSize);
        LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<Grade>().eq(gradeName != null, Grade::getName, gradeName);
        gradeService.page(page,wrapper);
        return R.ok(page).message("查询成功!!!");
    }

    @PostMapping("/saveOrUpdateGrade")
    public R saveOrUpdate(@RequestBody Grade grade)
    {
        //假设是插入新数据
        if(grade.getId() == null)
        {
            Grade one = gradeService.getOne(new LambdaQueryWrapper<Grade>()
                    .eq(Grade::getName, grade.getName()));
            if(one != null)
                return R.fail().message("当前年级已存在!!!");
        }
        gradeService.saveOrUpdate(grade);
        return R.ok().message("更新成功!!!");
    }

    @DeleteMapping("/deleteGrade")
    public R deleteGradeById(@RequestBody List<Long> list)
    {
        gradeService.removeBatchByIds(list);
        return R.ok().message("删除成功!!!");
    }
}
