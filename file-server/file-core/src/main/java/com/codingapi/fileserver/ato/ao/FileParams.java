package com.codingapi.fileserver.ato.ao;

import com.codingapi.fileserver.db.fastdfs.utils.InputStreamCacher;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 文件基本参数
 */
public class FileParams {
    private Integer blockIndex;
    private Integer blockNum;
    private String md5;
    private InputStreamCacher inputStreamCacher;
    private String suffix;

    public FileParams() {

    }

    public FileParams(InputStreamCacher inputStreamCacher, Integer blockIndex, Integer blockNum, String md5, String suffix) {
        this.blockIndex = blockIndex;
        this.blockNum = blockNum;
        this.md5 = md5;
        this.inputStreamCacher = inputStreamCacher;
        this.suffix = suffix;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public InputStreamCacher getInputStreamCacher() {
        return inputStreamCacher;
    }

    public void setInputStreamCacher(InputStreamCacher inputStreamCacher) {
        this.inputStreamCacher = inputStreamCacher;
    }
}
