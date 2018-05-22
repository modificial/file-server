package com.codingapi.fileserver.ato.ao;

import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author modificial
 * @date 2018/4/24 0024
 * @company codingApi
 * @description 图片修改传递参数
 */
public class ImageParam {
    /**
     * 文件流
     */
    @ApiParam(value = "图片文件", required = true)
    private MultipartFile multipartFile;
    /**
     * 图片md5值
     */
    @ApiParam(value = "图片MD5值", required = true)
    private String md5;
    /**
     * 令牌
     */
    @ApiParam(value = "令牌", required = true)
    private String token;

    public ImageParam(MultipartFile multipartFile, String md5, String token) {
        this.multipartFile = multipartFile;
        this.md5 = md5;
        this.token = token;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ImageParam{" +
                "multipartFile=" + multipartFile +
                ", md5='" + md5 + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
