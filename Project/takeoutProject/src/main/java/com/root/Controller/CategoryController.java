package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Common.R;
import com.root.pojo.Category;
import com.root.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<Category>> getPageCategory(@RequestParam("page") Integer page,
                                             @RequestParam("pageSize") Integer pageSize)
    {
        Page<Category> pageNum = new Page<>(page,pageSize);
        categoryService.page(pageNum, new QueryWrapper<Category>().orderByAsc("sort"));
        return R.success(pageNum);
    }

    @PostMapping
    public R<String> addClass(@RequestBody Category category)
    {
        String name = category.getName();
        Integer type = category.getType();
        //后端校验
        if(type == 1 && name.contains("套餐"))
            return R.error("添加的不是菜品!!!");
        else if(type == 2 && !name.contains("套餐"))
            return R.error("添加的不是套餐!!!");
        categoryService.save(category);
        return R.success("添加成功!!!");
    }

    @DeleteMapping
    public R<String> deleteById(Long ids)
    {
        categoryService.removeByConditional(ids);
        return R.success("删除成功!!!");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category)
    {
        categoryService.updateById(category);
        return R.success("信息修改成功");
    }

    //根据类型type(1.表示分类为菜品2表示套餐分类)查出所有Category category
    @GetMapping("/list")
    public R<List<Category>> getCategoryByType(Category category)
    {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType() != null,Category::getType,category.getType())
                .orderByAsc(Category::getSort);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }

}
