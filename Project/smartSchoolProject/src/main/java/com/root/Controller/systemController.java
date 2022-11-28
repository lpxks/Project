package com.root.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.root.Utils.CreateVerifiCodeImage;
import com.root.Utils.JwtHelper;
import com.root.Utils.MD5;
import com.root.Utils.R;
import com.root.pojo.Admin;
import com.root.pojo.Student;
import com.root.pojo.Teacher;
import com.root.pojo.loginUser;
import com.root.service.AdminService;
import com.root.service.StudentService;
import com.root.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RequestMapping("/sms/system")
@RestController
public class systemController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Value("${file.location}")
    private String basePath;


    //获取一张验证码图片
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取一张验证码图片
        BufferedImage image = CreateVerifiCodeImage.getVerifiCodeImage();
        String code =  new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码保存至session域中 下次登录要比对
        ServletOutputStream outputStream = null;
        request.getSession().setAttribute("code",code);
        log.info("当前获取的验证码为:{}",code);
        try {
            outputStream = response.getOutputStream();
            //将其以流的形式写给浏览器
            ImageIO.write(image,"jpg",outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody loginUser loginUser, HttpSession session)
    {
        if(loginUser == null || loginUser.getUserType() == null)
           R.fail().message("当前登录用户信息为空!!!");
        HashMap<String, Object> maps = new HashMap<>();
        //验证码无效 或者不相等的话直接返回错误
        if(session.getAttribute("code") == null || !loginUser.getVerifiCode().equalsIgnoreCase((String)session.getAttribute("code")))
            return R.fail().message("当前验证码输入有误 请刷新后重新输入");
        //从session域中移除掉验证码
        session.removeAttribute("code");
        Integer type = loginUser.getUserType();
        //生成的token指令
        String token = null;
        switch (type)
        {
            case 1:
                Admin admin = adminService.getOne(new LambdaQueryWrapper<Admin>()
                        .eq(Admin::getName, loginUser.getUsername())
                        .eq(Admin::getPassword, MD5.encrypt(loginUser.getPassword())));
                if(admin == null)
                {
                    return R.fail().message("用户名或者账号有误!!!");
                }
                token = JwtHelper.createToken(admin.getId(),type);
                break;
            case 2:
                Student student = studentService.getOne(new LambdaQueryWrapper<Student>()
                        .eq(Student::getName,loginUser.getUsername())
                        .eq(Student::getPassword,MD5.encrypt(loginUser.getPassword())));
                if(student == null)
                    return R.fail().message("用户名或者账号有误!!!");
                token = JwtHelper.createToken(student.getId().longValue(),type);
                break;
            case 3:
                Teacher teacher = teacherService.getOne(new LambdaQueryWrapper<Teacher>()
                        .eq(Teacher::getName,loginUser.getUsername())
                        .eq(Teacher::getPassword,MD5.encrypt(loginUser.getPassword())));
                if(teacher == null)
                    return R.fail().message("用户名或者账号有误!!!");
                token = JwtHelper.createToken(teacher.getId().longValue(),type);
        }
        maps.put("token",token);
        return R.ok(maps).message("登录成功!!!");
    }

    @GetMapping("/getInfo")
    public R getInfo(@RequestHeader("token") String token)
    {
        //带来的token为空或者token已过期的话直接返回
        if(token == null || JwtHelper.isExpiration(token))
            return R.fail().message("当前登录异常!!!");
        Long userId = JwtHelper.getUserId(token);
        Integer type = JwtHelper.getUserType(token);
        HashMap<String, Object> maps = new HashMap<>();
        switch (type)
        {
            case 1:
                Admin admin = adminService.getById(userId);
                maps.put("user",admin);
                break;
            case 2:
                Student student = studentService.getById(userId);
                maps.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getById(userId);
                maps.put("user",teacher);
                break;
        }
        maps.put("userType",type);
        return R.ok(maps).message("登录成功!!!");
    }

    //头像的上传
    @PostMapping("/headerImgUpload")
    public R fileUpload(@RequestPart("multipartFile") MultipartFile file) {
        //上传的图片非空再进行保存
        if(!file.isEmpty())
        {
            File dir = new File(basePath);
            //假设当前文件夹不存在就创建一个文件夹
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //生成一个新的文件名称
            String newFileName = UUID.randomUUID().toString();
            //获取原始的文件名称
            String name = file.getOriginalFilename();

            //将文件的后缀截取出来
            String suffix = name.substring(name.lastIndexOf("."));
            try {
                file.transferTo(new File(basePath + newFileName + suffix));
            } catch (IOException e) {
                return R.fail().message("文件上传出现问题");
            }
            //将文件名称返回给客户端
            return R.ok("/sms/system/fileDownLoad/"+newFileName + suffix);
        }
        return R.fail().message("文件上传异常!!!");
    }

    //将图片响应给浏览器
    @GetMapping("/fileDownLoad/{name}")
    public void responseImg(@PathVariable("name")String fileName,HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(new File(basePath + fileName));
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @PostMapping("/updatePwd/{name}/{psd}")
    public R UpdatePassWord(@PathVariable("name") String name
                            ,@PathVariable("psd") String psd,
                            @RequestHeader("token") String token)
    {
        if(token == null && !JwtHelper.isExpiration(token))
            return R.fail().message("您当前权限不够");
        if(psd.length() < 5)
            return R.fail().message("密码长度要不低于5位");
        Long userId = JwtHelper.getUserId(token);
        Admin one = adminService.getOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getId, userId));
        //看看输入的旧密码是否正确
        if(MD5.encrypt(name).equals(one.getPassword())) {
            one.setPassword(MD5.encrypt(psd));
            adminService.updateById(one);
            return R.ok().message("密码更新成功!!!");
        }
        return R.fail().message("抱歉您输入的旧密码错误!!!");
    }
}
