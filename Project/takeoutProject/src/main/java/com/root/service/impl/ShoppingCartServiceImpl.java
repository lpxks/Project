package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.ShoppingCart;
import com.root.service.ShoppingCartService;
import com.root.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-09-28 14:15:46
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




