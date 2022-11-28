package com.root.service;

import com.root.pojo.DishFlavor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuzexiong
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service
* @createDate 2022-09-27 09:07:39
*/
public interface DishFlavorService extends IService<DishFlavor> {

    public void  removeBatchByDishIds(String ids);

}
