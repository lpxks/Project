package com.root.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author liuzexiong
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-09-28 10:14:17
* @Entity com.root.pojo.Orders
*/
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

//    @Select("SELECT * FROM orders,order_detail WHERE orders.`id` = order_detail.`order_id` " +
//            "AND user_id = ${userId}")

    @Select("SELECT * FROM orders WHERE user_id = ${userId} order by order_time desc")
    public Page<Orders> getOrdersByPhone(Page<Orders> page, String userId);

}




