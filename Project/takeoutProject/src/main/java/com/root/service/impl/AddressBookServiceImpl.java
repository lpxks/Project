package com.root.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.pojo.AddressBook;
import com.root.service.AddressBookService;
import com.root.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author liuzexiong
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-09-23 16:30:23
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




