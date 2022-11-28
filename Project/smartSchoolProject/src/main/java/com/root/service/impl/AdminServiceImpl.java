package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.Admin;
import com.root.service.AdminService;
import com.root.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-09-30 16:21:34
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




