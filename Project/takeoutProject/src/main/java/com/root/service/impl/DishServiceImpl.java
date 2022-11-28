package com.root.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.Dto.DishDto;
import com.root.mapper.DishFlavorMapper;
import com.root.pojo.Dish;
import com.root.pojo.DishFlavor;
import com.root.service.DishFlavorService;
import com.root.service.DishService;
import com.root.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liuzexiong
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-09-25 19:56:46
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public Page<Dish> getAllDishes(Page<Dish> page,String name) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        return dishMapper.getDishes(page,name);
    }


    //修改页面数据
    @Override
    public DishDto getDishDto(Long id) {
        DishDto dishDto = dishMapper.getDishDto(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, id));
        dishDto.setFlavors(dishFlavors);
        return dishDto;
    }

    @Override
    public void updateByIds(String ids,Integer status) {
        dishMapper.updateByIds(ids,status);
    }

    @Override
    public List<DishDto> getDishDtoList(Dish dish) {
        return dishMapper.getDishList(dish.getCategoryId());
    }


}




