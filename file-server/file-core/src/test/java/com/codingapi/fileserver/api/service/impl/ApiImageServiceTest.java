package com.codingapi.fileserver.api.service.impl;

import com.codingapi.fileserver.api.service.ApiImageService;
import com.codingapi.fileserver.ato.ao.ImageParam;
import com.lorne.core.framework.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author modificial
 * @date 2018/4/25 0025
 * @company codingApi
 * @description 需要开启的服务有linux下的FASTDFS的tracker和storage服务和nginx服务
 * 需要准备磁盘准备一张图片
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApiImageServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiImageServiceTest.class);
    @Autowired
    private ApiImageService apiImageService;
    private String md5;
    private File file;
    private ImageParam imageParam;
    private String token;
    private Integer width;
    private Integer height;
    private String key;

    /**
     * 初始化参数
     *
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        file = new File("D:/timg.jpg");
        md5 = DigestUtils.md5DigestAsHex(Files.newInputStream(Paths.get("D:/timg.jpg")));
        MultipartFile multipartFile = new MockMultipartFile(file.getAbsolutePath(), Files.newInputStream(file.toPath()));
        imageParam = new ImageParam(multipartFile, md5, "kkk");
        key = "438726163858313216";
        width = 200;
        height = 200;
        token = "kkkkkkkkkkkkkkkkkk";
    }

    /**
     * 测试文件上传
     *
     * @throws ServiceException
     */
    @Test
    public void testUploadImage() throws ServiceException {

        Assert.notNull(apiImageService.upload(imageParam), "上传图片失败");
        LOGGER.info("开始返回上传反馈信息{" + apiImageService.upload(imageParam) + "}");
    }

    @Test
    public void testDownloadImage() throws ServiceException {
        Assert.notNull(apiImageService.download(token, key, width, height), "下载图片失败");
        LOGGER.info("开始返回下载文件反馈信息{" + apiImageService.download(token, key, width, height) + "}");
    }

    @Test
    public void testDeleteImage() throws ServiceException {
        Assert.isTrue(apiImageService.delete(key) == 0, "删除文件失败");
        LOGGER.info("开始返回删除文件反馈信息{" + apiImageService.delete(key) + "}");
    }
}
