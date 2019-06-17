package com.hamster.chat.config;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * springMVC拦截器
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {
    @Value("${upload.file.path}")
    private String uploadPath;


    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
    //静态文件处理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //http://127.0.0.1:8080/file/down/7ba688e2-43a9-11e9-a79f-0050568bfde6
        String pathPattern = "/image/**";
        String location = "file:" + uploadPath;
        registry.addResourceHandler(pathPattern).addResourceLocations(location);
//        super.addResourceHandlers(registry);
    }
    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }
}
