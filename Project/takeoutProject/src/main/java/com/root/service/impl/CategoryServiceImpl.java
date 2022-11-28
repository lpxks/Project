package com.root.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.Exceptions.CustomerException;
import com.root.pojo.Category;
import com.root.pojo.Dish;
import com.root.pojo.Setmeal;
import com.root.service.CategoryService;
import com.root.mapper.CategoryMapper;
import com.root.service.DishService;
import com.root.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-09-25 10:59:07
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryMapper categoryMapper;

    //删除某一个菜品
    @Override
    public void removeByConditional(Long ids) {

        //查出此菜品关联的菜的数量
        long count1 = dishService.count(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, ids));
        if(count1 > 0)
        {
            throw new CustomerException("当前分类下关联了一些菜 不可删除");
        }

        long count2 = setmealService.count(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId, ids));
        if(count2 > 0)
        {
            throw new CustomerException("当前分类下关联了一些套餐 不可删除");
        }

        //在都没有关联的情况下 之间删除
        categoryMapper.deleteById(ids);
    }
}




