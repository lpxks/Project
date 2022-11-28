package com.root.service;

import com.root.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuzexiong
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-09-25 10:59:07
*/
public interface CategoryService extends IService<Category> {

    public void removeByConditional(Long ids);
}
