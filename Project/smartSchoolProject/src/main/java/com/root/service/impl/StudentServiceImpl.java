package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.Student;
import com.root.service.StudentService;
import com.root.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-09-30 19:56:02
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}




