package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.root.Utils.R;
import com.root.pojo.Clazz;
import com.root.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClassController {

    @Autowired
    private ClazzService clazzService;

    //查询所有的班级信息展示在下拉框中
    @GetMapping("/getClazzs")
    public R<List<Clazz>> getAllClazz()
    {
       return R.ok(clazzService.list(new LambdaQueryWrapper<Clazz>().select(Clazz::getName)));
    }
}
