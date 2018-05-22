package com.codingapi.fileserver.db.fastdfs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author modificial
 * @date 2018/4/23 0023
 * @company codingApi
 * @description
 */
public class InputStreamCacher {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputStreamCacher.class);

    /**
     * 将InputStream中的字节保存到ByteArrayOutputStream中。
     */
    private ByteArrayOutputStream byteArrayOutputStream = null;

    public InputStreamCacher(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }

        byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public InputStream getInputStream() {
        if (byteArrayOutputStream == null) {
            return null;
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public void close() {
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
