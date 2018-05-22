package com.codingapi.fileserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author lorne
 * create by lorne on 2018/3/28
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Value("${codingapi.swagger2.pattern}")
    private String pattern;

    @Value("${codingapi.swagger2.title}")
    private String title;

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .build()
                .directModelSubstitute(java.sql.Timestamp.class, java.sql.Date.class)
                .enableUrlTemplating(false);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description("文件服务中心")
                .version("1.0")
                .build();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(DigestUtils.md5DigestAsHex(Files.newInputStream(Paths.get("D:/1234.png"))));
    }
}