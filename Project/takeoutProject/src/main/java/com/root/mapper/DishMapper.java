package com.root.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Dto.DishDto;
import com.root.pojo.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.root.pojo.DishFlavor;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.sql.Wrapper;
import java.util.List;

/**
* @author liuzexiong
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-09-25 19:56:46
* @Entity com.root.pojo.Dish
*/

@Repository
public interface DishMapper extends BaseMapper<Dish> {

    public Page<Dish> getDishes(Page<Dish> page,String name);

    @Select("SELECT * FROM dish WHERE dish.id = #{id}")
    public DishDto getDishDto(Long id);

    //# 会在动态的取出值 并且会加上''
    // $是类似于拼串 加值拼接在sql语句中
    @Update("update dish set status = #{status} where id in ${ids}")
    void updateByIds(String ids,Integer status);

    @Select("select * from dish where category_id = #{id}")
    List<DishDto> getDishList(Long id);
}




