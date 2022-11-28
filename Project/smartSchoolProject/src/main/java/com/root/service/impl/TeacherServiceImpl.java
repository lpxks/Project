package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.Teacher;
import com.root.service.TeacherService;
import com.root.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-09-30 19:56:06
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

}




