package com.codingapi.fileserver.ato.ao;

/**
 * @author modificial
 * @date 2018/4/20 0020
 * @company codingApi
 * @description 临时文件信息
 */
public class TempFileRes {
    /**
     * 文件有效性
     */
    private boolean isValid;
    /**
     * 临时文件路径
     */
    private String tempFilePath;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    @Override
    public String toString() {
        return "TempFileRes{" +
                "isValid=" + isValid +
                ", tempFilePath='" + tempFilePath + '\'' +
                '}';
    }
}
