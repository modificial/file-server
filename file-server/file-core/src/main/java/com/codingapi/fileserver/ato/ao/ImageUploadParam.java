package com.codingapi.fileserver.ato.ao;

import java.io.InputStream;

/**
 * @author modificial
 * @date 2018/5/16 0016
 * @company codingApi
 * @description ios图片上传参数
 */
public class ImageUploadParam {

    private String orginName;


    private InputStream inputStream;


    public String getOrginName() {
        return orginName;
    }

    public void setOrginName(String orginName) {
        this.orginName = orginName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
