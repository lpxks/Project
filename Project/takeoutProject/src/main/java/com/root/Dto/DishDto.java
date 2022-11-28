package com.root.Dto;

import com.root.pojo.Dish;
import com.root.pojo.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//页面联调的对象对于响应页面的数据应当是一个对象对应一个请求所响应的所有数据
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto extends Dish {

    //口味集合
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

}
