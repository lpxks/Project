package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.root.Common.R;
import com.root.Utlis.ValidateCodeUtils;
import com.root.pojo.User;
import com.root.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
public class CodeController {
    @Autowired
    private UserService userService;

    //获取验证码
    @GetMapping("/code/getCode")
    public R<String> getPhoneCode(String phone, HttpServletRequest httpServletRequest)
    {
        if(phone != null) {
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            //将电话号码:验证码存到session域中
            httpServletRequest.getSession().setAttribute(phone, code);
            return R.success(code);
        }
        return R.error("获取失败!!!");
    }

    @PostMapping("/user/login")
    public R<User> login(@RequestBody Map<String,String> phone
            , HttpServletRequest request)
    {
        //获取传过来的phone
        String phones = phone.get("phone");
        //获取传过来的code
        String code = phone.get("code");
        //根据电话号码获取对应的真正的code
        String realCode = (String) request.getSession().getAttribute(phones);
        if(realCode != null && realCode.equals(code)) {
            //登录成功将电话号码存起来 可以用于下次的过滤器的检验
            request.getSession().setAttribute("user",phones);
            //表示第一次登录 让其完成注册
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone,phones));
            if(user == null)
            {
                user.setPhone(phones);
                user.setStatus(1);
                userService.save(user);
//                request.getSession().setAttribute("user", phone);
            }
            request.getSession().setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败!!!");
    }

    @PostMapping("/user/loginout")
    public R<String> logout(HttpSession httpSession)
    {
        httpSession.removeAttribute("user");
        return R.success("退出登录成功!!!");
    }

}
