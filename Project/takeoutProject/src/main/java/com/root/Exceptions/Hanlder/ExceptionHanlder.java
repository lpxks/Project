package com.root.Exceptions.Hanlder;

import com.root.Common.R;
import com.root.Exceptions.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

//全局异常处理
@Slf4j
@ControllerAdvice(value = "com.root.Controller")
public class ExceptionHanlder {

    @ResponseBody
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public R<String> responseException(SQLIntegrityConstraintViolationException exception){
        String message = exception.getMessage();
            String[] mes = message.split(" ");
            return R.error(mes[2] + "已被被占用!!!");
    }

    //关联了菜品时 抛出此异常
    @ResponseBody
    @ExceptionHandler({CustomerException.class})
    public R<String> responseECustomerException(CustomerException exception){
        return R.error(exception.getMessage());
    }
}
