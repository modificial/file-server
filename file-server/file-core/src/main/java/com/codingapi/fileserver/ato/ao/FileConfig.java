package com.codingapi.fileserver.ato.ao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author modificial
 * @date 2018/5/10 0010
 * @company codingApi
 * @description
 */
@ConfigurationProperties(prefix = "codingapi.upload")
@Component
public class FileConfig {
    private String tempdir;
    private long maxsize;

    public String getTempdir() {
        return tempdir;
    }

    public void setTempdir(String tempdir) {
        this.tempdir = tempdir;
    }

    public long getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(long maxsize) {
        this.maxsize = maxsize;
    }
}
