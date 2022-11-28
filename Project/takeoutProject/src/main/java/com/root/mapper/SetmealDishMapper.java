package com.root.mapper;

import com.root.pojo.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
* @author liuzexiong
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2022-09-27 16:51:07
* @Entity com.root.pojo.SetmealDish
*/
@Repository
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

    void deleteBatchSetMealIds(String ids, LocalDateTime updateTime,long userId);
}




