package com.root.test;

import org.junit.jupiter.api.Test;

import java.io.*;

public class tesPath {


    @Test
    public void test() throws IOException {
        File file = new File("E:\\项目源码\\Java项目源码\\Project\\Project\\takeoutProject\\src\\main\\resources\\static\\backend\\pictures\\8dcfda14-5712-4d28-82f7-ae905b3c2308.jpg");
        FileOutputStream outputStream = new FileOutputStream(new File("E:\\项目源码\\Java项目源码\\Project\\Project\\takeoutProject\\src\\main\\resources\\static\\backend\\pictures\\1.jpg"));
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[100];
        int len;
        while((len = inputStream.read(bytes)) != -1)
        {
            outputStream.write(bytes,0,len);
        }
        outputStream.close();
        inputStream.close();
    }

    @Test
    public void test01()
    {
        String str = "1,2,3";
        str.split(",");
        System.out.println(str);
    }
}
