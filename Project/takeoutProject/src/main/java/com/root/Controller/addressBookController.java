package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.root.Common.R;
import com.root.Utlis.ThreadLocalUtils;
import com.root.pojo.AddressBook;
import com.root.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/addressBook")
public class addressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> getAllAddress()
    {
        return R.success(addressBookService.list());
    }

    @PutMapping("/default")
    public R<String> updateDefaultAddress(@RequestBody AddressBook addressBook)
    {
        //先将修改默认的地址标志为0
        addressBookService.update(new LambdaUpdateWrapper<AddressBook>()
                        .eq(AddressBook::getIsDefault, 1).set(AddressBook::getIsDefault,0));
        //再修改指定id地址为默认地址
        addressBookService.update(new LambdaUpdateWrapper<AddressBook>()
                .eq(AddressBook::getId,addressBook.getId()).set(AddressBook::getIsDefault,1));
        return R.success("修改默认地址成功!!!");
    }
    @PostMapping
    public R<String> savaAddress(@RequestBody AddressBook addressBook)
    {
        addressBook.setUserId(ThreadLocalUtils.getData());
        addressBookService.save(addressBook);
        return R.success("添加地址成功!!!");
    }

    @GetMapping("/{id}")
    public R<AddressBook> getAddressById(@PathVariable("id") long id)
    {
        return R.success(addressBookService.getOne(new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getId,id)));
    }

    @PutMapping
    public R<String> updateAddress(@RequestBody AddressBook addressBook)
    {
        addressBookService.updateById(addressBook);
        return R.success("地址信息修改成功!!!");
    }

    @DeleteMapping
    public R<String> deleteAddress(String ids)
    {
        addressBookService.removeById(ids);
        return R.error("地址删除成功!!!");
    }

    //获取默认地址
    @GetMapping("/default")
    public R<AddressBook> getDefaultAddress()
    {
        AddressBook addDefault = addressBookService.getOne(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getIsDefault, 1));
        return R.success(addDefault);
    }

}
