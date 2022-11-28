package com.root.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.root.Dto.SetmealDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetMealDtoMapper extends BaseMapper<SetmealDto> {

}
