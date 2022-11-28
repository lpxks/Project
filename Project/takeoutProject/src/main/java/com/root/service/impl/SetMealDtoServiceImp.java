package com.root.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.Dto.SetmealDto;
import com.root.mapper.SetMealDtoMapper;
import com.root.mapper.SetmealDishMapper;
import com.root.mapper.SetmealMapper;
import com.root.pojo.SetmealDish;
import com.root.service.SetMealDtoService;
import com.root.service.SetmealDishService;
import com.root.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetMealDtoServiceImp extends ServiceImpl<SetMealDtoMapper, SetmealDto> implements SetMealDtoService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public SetmealDto getSetMealDtoById(Long setMealId) {
        //查套餐信息
        SetmealDto setMealDto = setmealMapper.getSetMealDtoById(setMealId);

        //查询菜品信息
        List<SetmealDish> lists = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, setMealId));
        setMealDto.setSetmealDishes(lists);


        return setMealDto;
    }
}
