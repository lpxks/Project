package com.root.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liuzexiong
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-09-26 15:56:17
*/
public interface SetmealService extends IService<Setmeal> {

    Page<Setmeal> getMyPage(Page<Setmeal> pageInfo,String name);

    public void updateBatchByids(String ids,Integer status);

    public void removeBatchByMyIds(List<Long> ids);
}
