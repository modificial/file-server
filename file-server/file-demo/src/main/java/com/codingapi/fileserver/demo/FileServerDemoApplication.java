package com.codingapi.fileserver.demo;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author modificial
 * @date 2018/4/25 0025
 * @company codingApi
 * @description 文件服务启动类
 */
@EnableFeignClients
@SpringBootApplication
@RestController
@RequestMapping("/file/upload")
public class FileServerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServerDemoApplication.class, args);
    }

    @PostMapping("/image")
    @ApiOperation(value = "图片上传", notes = "文件上传")
    public String upload(MultipartFile file) {
        return "OK";
    }

    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }

}
