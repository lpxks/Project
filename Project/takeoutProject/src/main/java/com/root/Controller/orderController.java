package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Common.R;
import com.root.Dto.OrdersDto;
import com.root.Exceptions.CustomerException;
import com.root.Utlis.ThreadLocalUtils;
import com.root.pojo.OrderDetail;
import com.root.pojo.Orders;
import com.root.pojo.ShoppingCart;
import com.root.pojo.User;
import com.root.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order")
public class orderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressBookService addressBookService;
    //查询及其分页操作
    @GetMapping("/page")
    public R<Page<Orders>> getPageInfos(Integer page, Integer pageSize
            , String number, String beginTime,String endTime)
    {
        Page<Orders> pages = new Page<>(page, pageSize);
        return R.success(ordersService.page(pages,new LambdaQueryWrapper<Orders>()
                .eq(number != null,Orders::getNumber,number)
                .orderByAsc(Orders::getOrderTime)
                .between(beginTime != null && endTime != null,Orders::getOrderTime,beginTime,endTime)));
    }

    @GetMapping("/userPage")
    public R<Page<Orders>> getUserOrders(Integer page,Integer pageSize)
    {
        Page<Orders> dishPage = new Page<Orders>(page,pageSize);
        ordersService.getOrdersByPhone(dishPage);
        return R.success(dishPage);
    }

    //提交订单
    @PostMapping("/submit")
    @Transactional
    public R<String> submitOrder(@RequestBody Orders orders) {

        log.info("当前的数据为:{}", orders);
        Long userId = ThreadLocalUtils.getData();
        //先根据user_id得到所有点的菜
        List<ShoppingCart> DishLists =
                shoppingCartService.list(new LambdaQueryWrapper<ShoppingCart>()
                        .eq(ShoppingCart::getUserId, userId).orderByAsc(ShoppingCart::getCreateTime));
        //先生成订单
        //获取订单的总金额
        BigDecimal total = new BigDecimal(0);
        for (ShoppingCart dish : DishLists)
            total = total.add(dish.getAmount().multiply(new BigDecimal(dish.getNumber())));
        //设置总金额
        orders.setAmount(total);
        //设置订单的创建时间(此处设置为加的第一个菜的时间)
        orders.setOrderTime(DishLists.get(0).getCreateTime());
        //在往订单细节表中插入订单的详习数据

        //设置用户id
        orders.setUserId(ThreadLocalUtils.getData());
        //设置用户的结账时间
        orders.setCheckoutTime(LocalDateTime.now());
        //随机生成的订单号
        orders.setNumber(String.valueOf(IdWorker.getId()));
        //将当前订单的状态修改为待派送
        orders.setStatus(2);
        //当前客户的名称
        User user = userService.getById(ThreadLocalUtils.getData());
        orders.setUserName(user.getName());
        orders.setConsignee(user.getName());
        orders.setPhone(user.getPhone());
        if(orders.getAddressBookId() == null)
        {
            throw new CustomerException("地址异常无法配送!!!");
        }
        //设置配送地址
        orders.setAddress(addressBookService.getById(orders.getAddressBookId()).getDetail());
        //保存订单
        ordersService.save(orders);

        List<OrderDetail> list = new ArrayList<>();
        for (ShoppingCart shoppingCart : DishLists) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setOrderId(orders.getId());
            orderDetail.setAmount(shoppingCart.getAmount());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setNumber(shoppingCart.getNumber());
            list.add(orderDetail);
        }
        //批量插入
        orderDetailService.saveBatch(list);

        //清空购物车
        shoppingCartService.remove(new LambdaUpdateWrapper<ShoppingCart>().eq(ShoppingCart::getUserId,userId));
        return R.success("支付成功!!!祝您用餐愉快");
    }

    //修改订单的状态
    @PutMapping
    public R<String> updateOrder(@RequestBody Orders orders)
    {
        ordersService.update(new LambdaUpdateWrapper<Orders>()
                .eq(orders.getId() != null,Orders::getId, orders.getId())
                .set(orders.getStatus() != null,Orders::getStatus,orders.getStatus()));
        return R.success("派送成功!!!");
    }

    @PostMapping("/again")
    public R<String> getOrderAgain(@RequestBody Orders orders)
    {
        return R.success("操作成功!!!");
    }
}
