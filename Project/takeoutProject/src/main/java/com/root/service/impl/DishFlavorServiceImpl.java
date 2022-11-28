package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.DishFlavor;
import com.root.service.DishFlavorService;
import com.root.mapper.DishFlavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-09-27 09:07:39
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    //批量删除菜品id在字符串ids中的所有对象
    @Override
    public void removeBatchByDishIds(String ids) {
        dishFlavorMapper.deleteBatchDishIds(ids);
    }
}




