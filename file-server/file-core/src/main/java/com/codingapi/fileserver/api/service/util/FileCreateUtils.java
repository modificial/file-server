package com.codingapi.fileserver.api.service.util;

import com.lorne.core.framework.exception.ServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/4/20 0020
 * @company codingApi
 * @description
 */
public class FileCreateUtils {
    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileCreateUtils.class);

    private FileCreateUtils() {
        //阻止初始化
    }

    /**
     * 创建文件
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
        if (StringUtils.hasText(path)) {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    return file;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void split(String src, int mb, String dest) {
        File srcFile = new File(src);
        if (!srcFile.exists()) {
            return;
        }
        long countSize = srcFile.length();
        long fileSize = 1024 * 1024 * mb;
        int num = 0;
        if (countSize % fileSize == 0) {
            num = (int) (countSize / fileSize);
        } else {
            num = (int) (countSize / fileSize) + 1;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(srcFile);
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedOutputStream bos = null;
            byte[] bytes = new byte[1024 * 1024];
            int len = -1;
            for (int i = 1; i <= num; i++) {
                String newFile = dest + File.separator + i;
                bos = new BufferedOutputStream(new FileOutputStream(newFile));
                int count = 0;
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                    bos.flush();
                    count += len;
                    if (count >= fileSize) {
                        break;
                    }

                }
                bos.close();
            }
            bis.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void merge(String dest, List<String> filePaths) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            bis = null;
            byte[] bytes = new byte[1024 * 1024];
            int len = -1;
            for (int i = 0; i < filePaths.size(); i++) {
                bis = new BufferedInputStream(new FileInputStream(filePaths.get(i)));
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
        }

    }

    /**
     * 用于删除所有的临时文件
     *
     * @param tempPaths 临时文件集合
     */
    public static void deleteAll(List<String> tempPaths) throws ServiceException {
        if (tempPaths == null || tempPaths.isEmpty()) {
            return;
        }
        for (String path : tempPaths) {
            LOGGER.info("开始删除文件" + path);
            try {
                FileUtils.forceDelete(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("删除临时文件失败");
                throw new ServiceException("删除临时文件失败");
            }
        }
    }

}
