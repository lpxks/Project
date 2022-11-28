package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.Utils.MD5;
import com.root.Utils.R;
import com.root.pojo.Admin;
import com.root.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sms/adminController")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllAdmin/{pages}/{pageSize}")
    public R<Page<Admin>> getAdminPageInfo(String name, @PathVariable("pages")Integer page,
                                           @PathVariable("pageSize") Integer pageSize)
    {
        Page<Admin> pages = new Page<>(page, pageSize);
        adminService.page(pages,new LambdaQueryWrapper<Admin>()
                .like(name != null,Admin::getName,name));
        return R.ok(pages);
    }

    @PostMapping("/saveOrUpdateAdmin")
    public R savaOrUpdate(@RequestBody Admin admin)
    {
        if(admin == null)
        {
            R.fail().message("提交数据异常");
        }
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        adminService.saveOrUpdate(admin);
        return R.ok().message("保存成功!!!");
    }

    @DeleteMapping("/deleteAdmin")
    public R deleteAdmin(@RequestBody List<Long> list)
    {
        adminService.removeBatchByIds(list);
        return R.ok().message("删除成功!!!");
    }
}
