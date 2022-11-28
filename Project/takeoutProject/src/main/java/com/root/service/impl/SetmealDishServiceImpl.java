package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.Utlis.ThreadLocalUtils;
import com.root.pojo.SetmealDish;
import com.root.service.SetmealDishService;
import com.root.mapper.SetmealDishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author liuzexiong
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service实现
* @createDate 2022-09-27 16:51:07
*/
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>
    implements SetmealDishService{

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //批量已删除套餐中对应的菜品
    @Override
    public void deleteBySetMealId(String ids) {

        setmealDishMapper.deleteBatchSetMealIds(ids, LocalDateTime.now(), ThreadLocalUtils.getData());
    }
}




