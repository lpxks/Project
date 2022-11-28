package com.root.service;

import com.root.pojo.SetmealDish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuzexiong
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service
* @createDate 2022-09-27 16:51:07
*/
public interface SetmealDishService extends IService<SetmealDish> {

    public void deleteBySetMealId(String ids);
}
