package com.codingapi.fileserver.ato.ao;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @date 2018/4/20 0020
 * @company codingApi
 * @description
 */
public class ChunckReq {
    /**
     * 文件秘钥
     */
    private String key;
    /**
     * 文件加密串
     */
    private String md5;
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 第几块
     */
    private Integer blockIndex;
    /**
     * 一共有几块
     */
    private Integer blockNum;

    /**
     * 上传流对象
     */
    private MultipartFile multipartFile;

    public ChunckReq(String key, String md5, String token, Integer blockIndex, Integer blockNum, MultipartFile multipartFile) {
        this.key = key;
        this.md5 = md5;
        this.token = token;
        this.blockIndex = blockIndex;
        this.blockNum = blockNum;
        this.multipartFile = multipartFile;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Integer getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    public Integer getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Integer blockNum) {
        this.blockNum = blockNum;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "ChunckReq{" +
                "key='" + key + '\'' +
                ", md5='" + md5 + '\'' +
                ", token='" + token + '\'' +
                ", blockIndex=" + blockIndex +
                ", blockNum=" + blockNum +
                ", multipartFile=" + multipartFile +
                '}';
    }
}
