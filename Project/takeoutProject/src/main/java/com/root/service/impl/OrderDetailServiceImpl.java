package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.OrderDetail;
import com.root.service.OrderDetailService;
import com.root.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-09-28 17:12:32
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




