package com.root.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.Utlis.ThreadLocalUtils;
import com.root.pojo.Setmeal;
import com.root.service.SetmealService;
import com.root.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author liuzexiong
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-09-26 15:56:17
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public Page<Setmeal> getMyPage(Page<Setmeal> pageInfo,String name) {

        return setmealMapper.getPageInfo(pageInfo,name);
    }

    @Override
    public void updateBatchByids(String ids,Integer status) {
        setmealMapper.updateBatchByids(ids,status,LocalDateTime.now(), ThreadLocalUtils.getData());
    }

    @Override
    public void removeBatchByMyIds(List<Long> ids) {
        setmealMapper.deleteBatchIds(ids);
    }
}




