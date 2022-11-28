package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Common.R;
import com.root.Dto.SetmealDto;
import com.root.pojo.Dish;
import com.root.pojo.Setmeal;
import com.root.pojo.SetmealDish;
import com.root.service.DishService;
import com.root.service.SetMealDtoService;
import com.root.service.SetmealDishService;
import com.root.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//套餐管理Controller
@Slf4j
@RequestMapping("/setmeal")
@RestController
public class SetMealController {

    @Autowired
    private SetMealDtoService setMealDtoService;

    @Autowired
    private SetmealService setMealservice;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishService dishService;


    //查询分页数据
    @GetMapping("/page")
    public R<Page<Setmeal>> getPageInfo(Integer page,Integer pageSize,String name)
    {
//        DispatcherServlet
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        setMealservice.getMyPage(pageInfo,name);
        return R.success(pageInfo);
    }

    //改变套餐的售卖状态
    @PostMapping("/status/{status}")
    public R<String> updateInfo(@PathVariable("status") Integer status, String ids)
    {
        ids = "(" + ids + ")";
        setMealservice.updateBatchByids(ids,status);
//        setMealservice.update();
        return R.success("状态修改成功!!!");
    }

    //批量删除
    @DeleteMapping
    public R<String> deleteByIds(String ids)
    {
        List<Long> list = new ArrayList<>();
        String[] str = ids.split(",");
        for(String strs : str)
        {
            list.add(Long.parseLong(strs));
        }
        //从套餐表中删除此套餐
//        setMealservice.removeBatchByIds(list);
        ids = "(" + ids + ")";
        setMealservice.removeBatchByMyIds(list);
        //从setMeal_dish表中删除对应的菜品信息

        setmealDishService.deleteBySetMealId(ids);
        return R.success("批量删除成功!!!");
    }

    @PostMapping
    public R<String> saveSetMeal(@RequestBody SetmealDto setmealDto)
    {
        //先将往套餐表中插
        setMealservice.save(setmealDto);
        //再往setMeal_dish中插入
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for(SetmealDish dish : setmealDishes)
        {
            dish.setSetmealId(String.valueOf(setmealDto.getId()));//设置新插入套餐id
        }
        setmealDishService.saveBatch(setmealDishes);//批量插入
        return R.success("添加成功!!!");
    }

    @GetMapping("/{ids}")
    public R<SetmealDto> getSetMealDto(@PathVariable("ids") Long setMealId)
    {
        log.info("进来了?");
        return R.success(setMealDtoService.getSetMealDtoById(setMealId));
    }

    //修改套餐
    @PutMapping
    public R<String> getAllSetMeal(@RequestBody SetmealDto setmealDto)
    {
        setMealservice.updateById(setmealDto);

        setmealDishService.updateBatchById(setmealDto.getSetmealDishes());

        return R.success("修改成功!!!");
    }

    @GetMapping("/list")
    public R<List<Dish>> getDishByCategoryId(String categoryId)
    {
        return R.success(dishService.list(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId,categoryId)));
    }

    @GetMapping("/dish/{id}")
    public R<Dish> getDishById(@PathVariable("id")Long id)
    {
        return R.success(dishService.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId,id)));
    }
}
