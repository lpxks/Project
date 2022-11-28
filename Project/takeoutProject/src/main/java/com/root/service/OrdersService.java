package com.root.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Dto.OrdersDto;
import com.root.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuzexiong
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-09-28 10:14:17
*/
public interface OrdersService extends IService<Orders> {

    public Page<Orders> getOrdersByPhone(Page<Orders> page);
}
