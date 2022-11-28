package com.root.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.Utlis.ThreadLocalUtils;
import com.root.pojo.Orders;
import com.root.service.OrdersService;
import com.root.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-09-28 10:14:17
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

    @Autowired
    private OrdersMapper ordersMapper;
    @Override
    public Page<Orders> getOrdersByPhone(Page<Orders> page) {
        String userId = ThreadLocalUtils.getData().toString();
        ordersMapper.getOrdersByPhone(page,userId);
        return null;
    }
}




