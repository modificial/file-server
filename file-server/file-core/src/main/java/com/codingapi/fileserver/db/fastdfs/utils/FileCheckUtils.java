package com.codingapi.fileserver.db.fastdfs.utils;

import com.codingapi.fileserver.db.fastdfs.client.FdfsClient;
import com.lorne.core.framework.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description
 */
public class FileCheckUtils {
    /**
     * 图片类型
     */
    private static final List<String> TYPE_IMAGE = new ArrayList<>();
    /**
     * 文档类型
     */
    private static final List<String> TYPE_DOC = new ArrayList<>();
    /**
     * 音频类型
     */
    private static final List<String> TYPE_VIDEO = new ArrayList<>();
    /**
     * 压缩文件类型
     */
    private static final List<String> TYPE_COMPRESS = new ArrayList<>();
    /**
     * 默认分片文件最大大小
     */
    private static final long DEFAULT_MAX_SIZE = 2 * 1024 * 1024;

    static {
        TYPE_IMAGE.add("png");
        TYPE_IMAGE.add("gif");
        TYPE_IMAGE.add("jpeg");
        TYPE_IMAGE.add("jpg");

        TYPE_DOC.add("pdf");
        TYPE_DOC.add("ppt");
        TYPE_DOC.add("xls");
        TYPE_DOC.add("xlsx");
        TYPE_DOC.add("pptx");
        TYPE_DOC.add("doc");
        TYPE_DOC.add("docx");

        TYPE_VIDEO.add("mp3");
        TYPE_VIDEO.add("mp4");
        TYPE_VIDEO.add("flv");

        TYPE_COMPRESS.add("zip");
        TYPE_COMPRESS.add("rar");
    }

    /**
     * 检查图片类型. <br>
     * 默认检查 ['png', 'gif', 'jpeg', 'jpg'] 几种类型
     *
     * @param filename 文件名称
     * @return
     */
    public static boolean checkImage(String filename) {
        return checkImage(null, filename);
    }

    /**
     * 检查图片类型
     *
     * @param types    可自行传入文件的类型集合，默认检查 ['png', 'gif', 'jpeg', 'jpg'] 几种类型
     * @param filename 文件名称
     * @return
     */
    public static boolean checkImage(List<String> types, String filename) {
        List<String> checkTypes = types;
        if (types == null || types.size() == 0) {
            checkTypes = TYPE_IMAGE;
        }

        return checkType(checkTypes, filename);
    }

    /**
     * 检查文档类型. <br>
     * 默认检查 ['pdf', 'ppt', 'xls', 'xlsx', 'pptx', 'doc', 'docx'] 几种类型
     *
     * @param filename 文件名称
     * @return
     */
    public static boolean checkDoc(String filename) {
        return checkDoc(null, filename);
    }

    /**
     * 检查文档类型
     *
     * @param types    可自行传入文件的类型集合，默认检查 ['pdf', 'ppt', 'xls', 'xlsx', 'pptx', 'doc', 'docx'] 几种类型
     * @param filename 文件名称
     * @return
     */
    public static boolean checkDoc(List<String> types, String filename) {
        List<String> checkTypes = types;
        if (types == null || types.size() == 0) {
            checkTypes = TYPE_DOC;
        }

        return checkType(checkTypes, filename);
    }

    /**
     * 检查音频类型. <br>
     * 默认检查 ['mp3', 'mp4', 'flv'] 几种类型
     *
     * @param filename 文件名称
     * @return
     */
    public static boolean checkVideo(String filename) {
        return checkVideo(null, filename);
    }

    /**
     * 检查音频类型
     *
     * @param types    可自行传入文件的类型集合，默认检查 ['mp3', 'mp4', 'flv'] 几种类型
     * @param filename 文件名称
     * @return
     */
    public static boolean checkVideo(List<String> types, String filename) {
        List<String> checkTypes = types;
        if (types == null || types.size() == 0) {
            checkTypes = TYPE_VIDEO;
        }

        return checkType(checkTypes, filename);
    }

    /**
     * 检查压缩文件类型. <br>
     * 默认检查 ['zip', 'rar'] 几种类型
     *
     * @param filename 文件名称
     * @return
     */
    public static boolean checkCompress(String filename) {
        return checkCompress(null, filename);
    }

    /**
     * 检查压缩文件类型
     *
     * @param types    可自行传入文件的类型集合，默认检查 ['zip', 'rar'] 几种类型
     * @param filename 文件名称
     * @return
     */
    public static boolean checkCompress(List<String> types, String filename) {
        List<String> checkTypes = types;
        if (types == null || types.size() == 0) {
            checkTypes = TYPE_COMPRESS;
        }

        return checkType(checkTypes, filename);
    }

    /**
     * 检查类型通用方法
     */
    private static boolean checkType(List<String> checkTypes, String filename) {
        return checkTypes.contains(getFilenameSuffix(filename));
    }

    /**
     * 获取文件名称的后缀
     *
     * @param filename
     * @return 文件后缀
     */
    public static String getFilenameSuffix(String filename) {
        String suffix = null;
        if (StringUtils.isNotBlank(filename) && filename.contains(FdfsClient.POINT)) {
            suffix = filename.substring(filename.lastIndexOf(FdfsClient.POINT) + 1).toLowerCase();
        }
        return suffix;
    }

    /**
     * 检查文件大小是否允许上传
     *
     * @param size 每片文件大小
     * @return 0 不需要分片 1 需要分片
     */
    public static int checkFileSize(long size) {
        if (size <= 0 || size <= DEFAULT_MAX_SIZE) {
            return 0;
        }
        return 1;
    }

    /**
     * 拷贝一个输入流，解决输入流不能被重复读取的问题
     *
     * @param inputStream 输入流
     * @return
     */
    public static InputStream copyInputSream(InputStream inputStream) throws ServiceException {
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream, outputStream);
            return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
        } catch (IOException e) {
            throw new ServiceException("拷贝输入流失败");
        }
    }
}

