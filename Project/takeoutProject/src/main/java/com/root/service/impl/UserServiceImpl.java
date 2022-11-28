package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.User;
import com.root.service.UserService;
import com.root.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-09-28 10:21:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




