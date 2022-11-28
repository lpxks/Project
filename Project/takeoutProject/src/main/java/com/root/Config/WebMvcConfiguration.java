package com.root.Config;

import com.root.Common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    //添加消息转换器:将响应给浏览器的对象格式化为指定的数据类型
    //此处添加消息转换器的目的是为了弥补前端js将Long数据类型转换时的数据精度丢失缺陷
    //消息转换器又会调用对应的组件将对象转换为指定的数据格式
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //此消息转换器可以将对象转换为json对象
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter
                = new MappingJackson2HttpMessageConverter(new JacksonObjectMapper());
        converters.add(0,jackson2HttpMessageConverter);
        //DispatcherServlet
    }

    //静态资源请求时的映射路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/pictures/**")
                .addResourceLocations("classpath:/static/backend/pictures/");
    }
}
