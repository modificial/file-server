package com.codingapi.fileserver.ato.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 文件校验结果
 */
public class FileCheckResult {
    /**
     * 是否需要分片 0不需要分片 1需要分片
     */
    @ApiModelProperty(value = "是否需要分片", notes = "0 不需要，1 需要")
    private int isFragmentation;
    /**
     * 如果需要分片，分片的大小
     */
    @ApiModelProperty(value = "分块的大小")
    private int blockSize;
    /**
     * 应该分几块
     */
    @ApiModelProperty(value = "需要分几块")
    private int blockNumber;
    /**
     * 访问秘钥
     */
    @ApiModelProperty(value = "文件秘钥")
    private String key;

    public int getIsFragmentation() {
        return isFragmentation;
    }

    public void setIsFragmentation(int isFragmentation) {
        this.isFragmentation = isFragmentation;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "FileCheckResult{" +
                "isFragmentation=" + isFragmentation +
                ", blockSize=" + blockSize +
                ", blockNumber=" + blockNumber +
                ", key='" + key + '\'' +
                '}';
    }
}
