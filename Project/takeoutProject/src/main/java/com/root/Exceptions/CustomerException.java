package com.root.Exceptions;

//自定义的异常:在用户要删除谋菜品时 看看这个菜品下是否有某个菜或者
//套餐
public class CustomerException extends RuntimeException{

    public CustomerException(String message) {
        super(message);
    }
}
