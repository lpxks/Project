package com.root.Controller;

import com.root.Common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/common")
@Controller
public class CommonController {

    //从配置文件中的存储文件的路径取到
    @Value("${img.path}")
    private String BasePath;

    //上传文件
    @ResponseBody
    @PostMapping("/upload")
    public R<String> uploadFile(@RequestPart("file") MultipartFile name,HttpServletResponse response)
    {
        if(name.isEmpty())
            return R.error("文件为空!!!");
        String newFileName = UUID.randomUUID().toString();
        //获取文件的原始名称
        String originalFilename = name.getOriginalFilename();
        //截取文件的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        response.setContentType("image/jpeg");//设置响应的类型
        File mir = new File(BasePath);//基础文件夹 存放图片的文件夹
        if(!mir.exists())//假设不存在的话就创建一个文件夹
        {
            mir.mkdir();
        }
        try {
            //将文件转移至指定位置
            name.transferTo(new File(BasePath + newFileName + suffix));
        } catch (IOException e) {
        }finally {

        }
        return R.success(newFileName + suffix);
    }

    //将图片响应给浏览器
    @ResponseBody
    @GetMapping({"/download"})
    public void getImg(String name, HttpServletResponse response) throws IOException {
        //return "forward:/backend/pictures/" + name;
        FileInputStream inputStream = new FileInputStream(new File(BasePath + name));
        ServletOutputStream outputStream = response.getOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        while((len = inputStream.read(buffer)) != -1)
        {
            outputStream.write(buffer,0,len);
            outputStream.flush();
        }
        if(inputStream != null)
            inputStream.close();
        if(outputStream != null)
            outputStream.close();
    }

//    @GetMapping({"/download"})
//    public String getImg(String name, HttpServletResponse response) throws IOException {
//       return "forward:/backend/pictures/" + name;
//    }
}
