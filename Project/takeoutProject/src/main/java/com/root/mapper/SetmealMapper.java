package com.root.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Dto.SetmealDto;
import com.root.pojo.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author liuzexiong
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2022-09-26 15:56:17
* @Entity com.root.pojo.Setmeal
*/
@Repository
public interface SetmealMapper extends BaseMapper<Setmeal> {

    Page<Setmeal> getPageInfo(Page<Setmeal> pageInfo,String name);

    //修改状态
    //在查询分页数据时 mybatisPlus会自动帮我们加上limit字段

    void updateBatchByids(String ids, Integer status, LocalDateTime updateTime,Long updateId);


    @Select("SELECT * FROM setmeal where id = #{setMealId}")
    SetmealDto getSetMealDtoById(Long setMealId);
}




