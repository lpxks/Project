package com.root.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Dto.DishDto;
import com.root.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liuzexiong
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-09-25 19:56:46
*/
public interface DishService extends IService<Dish> {

    public Page<Dish> getAllDishes(Page<Dish> page,String name);

    public DishDto getDishDto(Long id);

    public void updateByIds(String ids,Integer status);

    public List<DishDto> getDishDtoList(Dish dish);
}
