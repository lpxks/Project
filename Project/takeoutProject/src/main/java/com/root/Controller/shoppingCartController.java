package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.root.Common.R;
import com.root.Utlis.ThreadLocalUtils;
import com.root.pojo.Dish;
import com.root.pojo.ShoppingCart;
import com.root.service.DishService;
import com.root.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/shoppingCart")
@Slf4j
@RestController
public class shoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    //查询购物车
    @GetMapping("/list")
    public R<List<ShoppingCart>> list()
    {
        return R.success(shoppingCartService.list(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, ThreadLocalUtils.getData())));

    }

    //添加至购物车
    @PostMapping("/add")
    public R<String> addToShoppingCart(@RequestBody ShoppingCart shoppingCart)
    {
        Long userId = ThreadLocalUtils.getData();
        log.info("添加数据{}",shoppingCart);
        ShoppingCart one = shoppingCartService.getOne(new LambdaQueryWrapper<ShoppingCart>()
                .eq(shoppingCart.getDishId() != null,ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getSetmealId() != null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId()));
        //没有的话直接保存
        if(one == null) {
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
        }
        else
        {
            //更新数量
            Integer count = one.getNumber() + 1;
            one.setNumber(count);
            one.setCreateTime(LocalDateTime.now());
            one.setUserId(ThreadLocalUtils.getData());
            shoppingCartService.updateById(one);
        }
        return R.success("添加成功!!!");
    }
    @DeleteMapping("/clean")
    public R<String> deleteCart()
    {
        shoppingCartService.remove(new LambdaUpdateWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, ThreadLocalUtils.getData()));
        return R.success("清空购物车成功!!!");
    }

    //减少某种商品的数量
    @PostMapping("/sub")
    public R<String> subTheDishNum(@RequestBody ShoppingCart shoppingCart)
    {
        Long userId = ThreadLocalUtils.getData();
        ShoppingCart one = shoppingCartService.getOne(new LambdaQueryWrapper<ShoppingCart>()
                .eq(shoppingCart.getDishId() != null,ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getSetmealId() != null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId()));
        one.setNumber(one.getNumber() - 1);
        if(one.getNumber() < 1) {
            shoppingCartService.remove(new LambdaUpdateWrapper<ShoppingCart>()
                    .eq(shoppingCart.getDishId() != null,ShoppingCart::getDishId,shoppingCart.getDishId())
                    .eq(shoppingCart.getSetmealId() != null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId()));
        }
        else
            shoppingCartService.updateById(one);
        return R.success("更改成功!!!");
    }
}
