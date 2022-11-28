package com.root.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.root.Dto.SetmealDto;

public interface SetMealDtoService extends IService<SetmealDto> {
   public SetmealDto getSetMealDtoById(Long setMealId);

}
