package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Common.R;
import com.root.Dto.DishDto;
import com.root.pojo.Dish;
import com.root.pojo.DishFlavor;
import com.root.service.DishFlavorService;
import com.root.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/dish")
@RestController
public class dishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @GetMapping("/page")
    public R<Page<Dish>> getPageInfo(Integer page, Integer pageSize,String name)
    {
        Page<Dish> pages = new Page<>(page,pageSize);
        dishService.getAllDishes(pages,name);
        return R.success(pages);
    }

    //不该菜品的售卖状态 0表示停售 1表示开售
    //批量起售与禁售
    @PostMapping("/status/{id}")
    public R<String> updateDishById(@PathVariable("id") Integer status,String ids)
    {
       log.info("当前获取的请求参数为:{}",ids);
       ids = "(" + ids + ")";
//        String[] split = ids.split(",");
//        List<String> Ids = Arrays.asList(split);
        dishService.updateByIds(ids,status);

       return R.success("修改成功!!!");
    }

    //页面修改数据时 查询数据将数据展示在页面上
    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable("id") Long id)
    {
        return R.success(dishService.getDishDto(id));
    }

    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto)
    {
//        log.info("提交过来的对象为: {},集合对象为: {}",dishDto.toString());
        //先保存dish对象生成dishId 再保存口味对象
        dishService.save(dishDto);
        for(DishFlavor dishFlavor : dishDto.getFlavors())
        {
            dishFlavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());
        return R.success("保存成功!!!");
    }

    //批量删除菜品 口味表也删除
    @DeleteMapping
    public R<String> deleteByIds(String ids)
    {
        String[] str = ids.split(",");
        List<Long> list = new ArrayList<>();
        for(String id : str)
            list.add(Long.parseLong(id));
        dishService.removeByIds(list);
        ids = "(" + ids + ")";
        dishFlavorService.removeBatchByDishIds(ids);
        return R.success("批量删除成功!!!");
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto)
    {
        log.info("当前的对象值为:{}",dishDto);
        dishService.updateById(dishDto);
        //先删掉再插入
        HashMap<String, Object> map = new HashMap<>();
        map.put("dish_id",dishDto.getId());
        dishFlavorService.removeByMap(map);
        List<DishFlavor> lists = dishDto.getFlavors();
        //再插入
        for(DishFlavor dishFlavor : lists) {
            dishFlavor.setDishId(dishDto.getId());
            dishFlavorService.save(dishFlavor);
        }
        return R.success("修改信息成功!!!");
    }

    @GetMapping("/list")
    public R<List<DishDto>> getDishByCategory(Dish dish)
    {
        //先查出所有的菜品分类信息
        List<DishDto> list = dishService.getDishDtoList(dish);
        for(DishDto dishDto : list)
        {
            Long id = dishDto.getId();
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id);
            //根据id查询口味
            List<DishFlavor> dishFlavorList = dishFlavorService.list(wrapper);
            //设置口味
            dishDto.setFlavors(dishFlavorList);
        }
        return R.success(list);
    }

    @GetMapping("/getDishById")
    public R<Dish> getDishById(String id)
    {
        return R.success(dishService.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId,id)));
    }
}
