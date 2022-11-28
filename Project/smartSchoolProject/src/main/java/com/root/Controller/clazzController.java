package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Utils.R;
import com.root.pojo.Clazz;
import com.root.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class clazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/getClazzsByOpr/{page}/{pageSize}")
    public R<Page<Clazz>> getClazzPageInfo(@PathVariable("page") Integer page,
                                           @PathVariable("pageSize") Integer pageSize
                                            ,String name)
    {
        Page<Clazz> pages = new Page<>(page, pageSize);
        clazzService.page(pages,new LambdaQueryWrapper<Clazz>()
                .like(name != null,Clazz::getName,name));
        return R.ok(pages);
    }

    @PostMapping("/saveOrUpdateClazz")
    public R savaOrUpdate(@RequestBody Clazz clazz)
    {
        if(clazz == null)
        {
            return R.fail().message("上传数据异常!!!");
        }

        //表示的是插入新的数据 但是班级存在
        if(clazz.getId() == null)
        {
            //先查询该班级存在吗
            Clazz one1 = clazzService.getOne(new LambdaQueryWrapper<Clazz>()
                    .eq(Clazz::getName, clazz.getName()));
            if(one1 != null)
                return R.fail().message("当前班级已存在!!!");
        }
        clazzService.saveOrUpdate(clazz);
        return R.ok().message("更新成功!!!");
    }

    @DeleteMapping("/deleteClazz")
    public R deleteClazz(@RequestBody List<Long> list)
    {
        clazzService.removeBatchByIds(list);
        return R.ok().message("删除成功!!!");
    }
}
