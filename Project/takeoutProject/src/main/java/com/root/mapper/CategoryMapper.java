package com.root.mapper;

import com.root.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author liuzexiong
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-09-25 10:59:07
* @Entity com.root.pojo.Category
*/
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}




