package com.codingapi.fileserver.service.impl;

import com.codingapi.fileserver.api.service.util.FileCreateUtils;
import com.codingapi.fileserver.api.service.util.VertifyUtil;
import com.codingapi.fileserver.ato.ao.*;
import com.codingapi.fileserver.db.fastdfs.client.FdfsClient;
import com.codingapi.fileserver.db.fastdfs.utils.InputStreamCacher;
import com.codingapi.fileserver.db.fastdfs.utils.SnowFlakeGenerator;
import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import com.codingapi.fileserver.exception.fastDFSException;
import com.codingapi.fileserver.service.FastDfsService;
import com.lorne.core.framework.exception.ServiceException;
import com.lorne.core.framework.utils.http.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @date 2018/4/7 0007
 * @company codingApi
 * @description 为文件管理业务层实现
 */
@Service
@EnableConfigurationProperties(FileConfig.class)
public class FastDfsServiceImpl implements FastDfsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastDfsServiceImpl.class);
    private static final String FILE_POINT = ".";
    @Autowired
    private FileConfig fileConfig;
    /**
     * 注入fastDFS客户端，实现文件管理
     */
    @Autowired
    private FdfsClient client;

    /**
     * @param fileParams 上传文件参数
     * @return
     * @throws fastDFSException
     * @throws ServiceException
     */
    @Override
    public UploadInfo upload(FileParams fileParams) throws ServiceException {
        VertifyUtil.vertify(fileParams);
        String md5 = fileParams.getMd5();
        TempFileRes tempFileRes = VertifyUtil.checkMd5(fileConfig.getTempdir(),
                fileParams.getInputStreamCacher().getInputStream(), md5);
        UploadInfo uploadInfo = new UploadInfo(false, false);
        if (!tempFileRes.isValid()) {
            return uploadInfo;
        }
        InputStreamCacher inputStreamCacher = fileParams.getInputStreamCacher();
        if (fileParams.getBlockIndex() < fileParams.getBlockNum()) {
            String fileId;
            try {
                fileId = client.upload(inputStreamCacher.getInputStream(), null);
            } catch (fastDFSException e) {
                LOGGER.error("上传文件失败");
                throw new ServiceException("上传文件失败");
            }
            UploadInfo uploadInfo1 = new UploadInfo(fileId, true, true, false, tempFileRes.getTempFilePath());
            inputStreamCacher.close();
            return uploadInfo1;
        } else if (fileParams.getBlockIndex().equals(fileParams.getBlockNum())) {
            String fileId;
            try {
                fileId = client.upload(inputStreamCacher.getInputStream(), null);
            } catch (fastDFSException e) {
                LOGGER.error("上传文件失败");
                throw new ServiceException("上传文件失败");
            }
            UploadInfo uploadInfo1 = new UploadInfo(fileId, true, true, true, tempFileRes.getTempFilePath());
            inputStreamCacher.close();
            return uploadInfo1;
        }
        return uploadInfo;
    }

    @Override
    public UploadInfo upload(InputStream inputStream, String ext, String md5) throws ServiceException {
        boolean isSigned = false;
        InputStreamCacher inputStreamCacher = new InputStreamCacher(inputStream);
        isSigned = VertifyUtil.checkMd5(inputStreamCacher.getInputStream(), md5);
        String fileId = "";
        if (!isSigned) {

            throw new ServiceException("文件MD5值校验失败");
        }
        try {
            fileId = client.upload(inputStreamCacher.getInputStream(), ext);
            LOGGER.info("关闭缓存流");
            inputStreamCacher.close();
            return new UploadInfo(fileId, true, true);
        } catch (fastDFSException e) {
            LOGGER.error("上传文件失败");
            throw new ServiceException("上传文件失败");
        }
    }

    /**
     * 下载文件
     *
     * @param filepaths 文件路径
     * @return 返回文件字节
     * @throws fastDFSException
     */
    @Override
    public String download(List<String> filepaths, String ext) throws ServiceException {
        LOGGER.info("开始下载文件+{" + filepaths + "}");
        File file = new File(fileConfig.getTempdir() + File.separator + System.currentTimeMillis() + FILE_POINT + ext);
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            for (String temp : filepaths) {
                byte[] bytes = client.download(temp);
                randomAccessFile.write(bytes);
                randomAccessFile.seek(randomAccessFile.length());
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("没有找到制定的文件");
            throw new ServiceException("没有找到指定的文件");
        } catch (fastDFSException e) {
            LOGGER.error("下载文件失败");
            throw new ServiceException("下载文件失败");
        } catch (IOException e) {
            LOGGER.error("文件流打开失败");
            throw new ServiceException("文件流打开失败");
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                LOGGER.error("关闭资源失败");
                throw new ServiceException("关闭资源失败");
            }
        }
        return file.getAbsolutePath();
    }

    /**
     * 删除文件
     *
     * @param filepaths 文件路径
     * @return 删除成功返回 0, 失败返回其它
     * @throws fastDFSException
     */
    @Override
    public int deleteFile(List<String> filepaths) throws ServiceException {
        int result = 1;
        for (String filepath : filepaths) {
            if (StringUtils.hasText(filepath)) {
                LOGGER.info("开始删除文件+{" + filepath + "}");
                try {
                    result = client.deleteFile(filepath);
                } catch (fastDFSException e) {
                    LOGGER.error("删除文件失败");
                    throw new ServiceException("删除文件失败");
                }
            }
            if (result != 0) {

                LOGGER.warn("文件{}" + FilenameUtils.getName(filepath) + "删除失败");

                continue;
            }
        }
        return result;
    }

    /**
     * 上传图片
     *
     * @param imageParam 图片参数
     * @return
     * @throws ServiceException
     */
    @Override
    public ImageInfo uploadImage(ImageParam imageParam) throws ServiceException {
        MultipartFile multipartFile = imageParam.getMultipartFile();
        boolean isVaild = false;
        try {
            isVaild = VertifyUtil.checkMd5(imageParam.getMultipartFile().getInputStream(), imageParam.getMd5());
            if (!isVaild) {
                LOGGER.error("文件MD5值检验失败");

                throw new ServiceException("文件MD5值检验失败");
            }
        } catch (IOException e) {
            LOGGER.error("读取文件信息失败");

            throw new ServiceException("读取文件信息出错");
        }
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        try {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(SnowFlakeGenerator.nextId());
            imageInfo.setOrginName(multipartFile.getOriginalFilename());
            imageInfo.setImageSize(multipartFile.getSize());
            imageInfo.setFileType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            String orginPath = client.upload(multipartFile.getInputStream(), ext);
            imageInfo.setOrginPath(orginPath);
            imageInfo.setValid(isVaild == true ? 1 : 0);
            imageInfo.setUploadTime(new Date());
            return imageInfo;
        } catch (IOException e) {
            LOGGER.error("读写文件失败");
            throw new ServiceException("读写文件失败");
        } catch (fastDFSException e) {
            LOGGER.error("读取文件失败");
            throw new ServiceException("文件上传失败");
        }
    }

    @Override
    public Map<String, File> downloadImage(String filePath, String ext) throws ServiceException {
        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(ext)) {
            LOGGER.warn("文件路径或后缀不能为空");

            throw new ServiceException("文件路径或后缀不能为空");
        }
        try {
            byte[] bytes = client.download(filePath);
            File tempFile = FileCreateUtils.createFile(fileConfig.getTempdir() + File.separator + UUID.randomUUID().toString() + FILE_POINT
                    + ext);
            FileUtils.writeByteArrayToFile(tempFile, bytes);
            File imageFile = FileCreateUtils.createFile(fileConfig.getTempdir() + File.separator + UUID.randomUUID().toString() + FILE_POINT
                    + ext);
            Map<String, File> map = new ConcurrentHashMap<>(2);
            map.put("tempFile", tempFile);
            map.put("imageFile", imageFile);
            return map;
        } catch (fastDFSException e) {
            LOGGER.error("下载文件失败");

            throw new ServiceException("下载文件失败");
        } catch (IOException e) {
            LOGGER.error("创建文件失败");

            throw new ServiceException("创建文件失败");
        }
    }

    @Override
    public ImageInfo uploadImage(String url) {
        String tempPath = fileConfig.getTempdir();
        String fileName = System.currentTimeMillis() + ".png";
        String path = tempPath + File.separator + fileName;
        boolean isDownload = HttpUtils.download(url, path);
        if (!isDownload) {
            throw new RuntimeException("图片文件下载失败");
        }
        Path path1 = Paths.get(path);
        File file = path1.toFile();
        String fileId;
        try {
            InputStream inputStream = Files.newInputStream(path1);
            fileId = client.upload(inputStream, FilenameUtils.getExtension(file.getAbsolutePath()));

        } catch (IOException e) {
            LOGGER.error("创建文件输入流失败");
            throw new RuntimeException("创建文件输入流失败");
        } catch (fastDFSException e) {
            throw new RuntimeException("文件上传失败");
        }
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(SnowFlakeGenerator.nextId());
        imageInfo.setOrginName(file.getName());
        imageInfo.setImageSize(file.length());
        imageInfo.setFileType(FilenameUtils.getExtension(file.getName()));
        imageInfo.setOrginPath(fileId);
        imageInfo.setValid(1);
        imageInfo.setUploadTime(new Date());
        return imageInfo;
    }
}