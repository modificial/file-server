package com.codingapi.fileserver.ato.ao;

import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author modificial
 * @date 2018/4/26 0026
 * @company codingApi
 * @description 单个文件上传参数
 */
public class FileUploadReq {
    /**
     * 访问令牌
     */
    @ApiParam(value = "访问令牌")
    private String token;
    @ApiParam(value = "文件md5校验值")
    private String md5;
    @ApiParam(value = "上传的文件")
    private MultipartFile multipartFile;

    public FileUploadReq(String token, String md5, MultipartFile multipartFile) {
        this.token = token;
        this.md5 = md5;
        this.multipartFile = multipartFile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "FileUploadReq{" +
                "token='" + token + '\'' +
                ", md5='" + md5 + '\'' +
                ", multipartFile=" + multipartFile +
                '}';
    }
}
