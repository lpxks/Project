package com.root.Common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.root.Utlis.ThreadLocalUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Supplier;

//公共数据的填充
//由mybatisPlus提供
@Component
public class MybatisPlusMetaData implements MetaObjectHandler {


    //插入数据的自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser", ThreadLocalUtils.getData());
        metaObject.setValue("updateUser",ThreadLocalUtils.getData());
    }

    //更新数据的自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",ThreadLocalUtils.getData());
    }

}
