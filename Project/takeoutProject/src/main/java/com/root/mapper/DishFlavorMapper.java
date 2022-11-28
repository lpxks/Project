package com.root.mapper;

import com.root.pojo.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
* @author liuzexiong
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-09-27 09:07:39
* @Entity com.root.pojo.DishFlavor
*/
@Repository
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    //根据菜品名删除对应的口味
    @Delete("delete from dish_flavor where dish_id in ${ids}")
    void deleteBatchDishIds(String ids);
}




