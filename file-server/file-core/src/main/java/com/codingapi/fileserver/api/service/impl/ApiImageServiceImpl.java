package com.codingapi.fileserver.api.service.impl;

import com.codingapi.fileserver.api.service.ApiImageService;
import com.codingapi.fileserver.api.service.util.VertifyUtil;
import com.codingapi.fileserver.ato.ao.ImageParam;
import com.codingapi.fileserver.ato.vo.ImageResponse;
import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import com.codingapi.fileserver.image.type.ImageType;
import com.codingapi.fileserver.service.FastDfsService;
import com.codingapi.fileserver.service.ImageHandleService;
import com.codingapi.fileserver.service.ImageInfoService;
import com.codingapi.netflix.framework.exception.ServerFeignException;
import com.google.common.collect.Lists;
import com.lorne.core.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author modificial
 * @date 2018/4/24
 * @company codingApi
 * @description 图片上传下载删除
 */
@Service
public class ApiImageServiceImpl implements ApiImageService {
    /**
     * 打印日志信息
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiImageServiceImpl.class);
    @Autowired
    private FastDfsService fastDfsService;
    @Autowired
    private ImageInfoService imageInfoService;
    @Autowired
    private ImageHandleService imageHandleService;

    /**
     * 上传图片
     *
     * @param imageParam
     * @return
     * @throws ServiceException
     */
    @Override
    public ImageResponse upload(ImageParam imageParam) throws ServiceException {
        MultipartFile multipartFile = imageParam.getMultipartFile();
        ImageInfo imageInfo = fastDfsService.uploadImage(imageParam);
        ImageResponse imageResponse = getResponse(imageInfo, multipartFile.getSize());

        return imageResponse;
    }

    /**
     * @param token  令牌
     * @param key    访问秘钥
     * @param width  图片宽度
     * @param height 图片高度
     * @return
     * @throws ServiceException
     */
    @Override
    public ResponseEntity<InputStreamResource> download(String token, String key, Integer width, Integer height) throws ServiceException {
        VertifyUtil.vertify(token, key, width, height);
        String filepaths;
        String fileName = null;
        String ext = null;
        ImageInfo imageInfo = imageInfoService.getImageInfoByKey(key);
        if (imageInfo == null) {
            LOGGER.error("图片信息获取失败");
            throw new ServiceException("图片信息获取失败");
        }
        filepaths = imageInfo.getOrginPath();
        fileName = imageInfo.getOrginName();
        ext = imageInfo.getFileType();
        Map<String, File> map = fastDfsService.downloadImage(filepaths, ext);
        File tempFile = map.get("tempFile");
        imageHandleService.imgNoScale(tempFile.getAbsolutePath(), map.get("imageFile").getAbsolutePath(), width, height, false);
        FileSystemResource file = new FileSystemResource(map.get("imageFile"));
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentType(MediaType.parseMediaType(ImageType.getMineType(ext)));
        } catch (ServerFeignException e) {
            LOGGER.error("类型转换失败");
            throw new ServiceException("类型转换失败");
        }
        InputStream inputStream = null;
        ResponseEntity<InputStreamResource> resourceResponseEntity;
        try {
            inputStream = file.getInputStream();
            resourceResponseEntity = ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .body(new InputStreamResource(inputStream));
            return resourceResponseEntity;
        } catch (IOException e) {
            LOGGER.error("文件下载失败{}" + fileName);
            throw new ServiceException("下载文件失败");
        }
    }

    /**
     * 删除图片
     *
     * @param key
     * @return
     * @throws ServiceException
     */
    @Override
    public int delete(String key) throws ServiceException {
        ImageInfo imageInfo = imageInfoService.getImageInfoByKey(key);
        if (imageInfo == null) {
            LOGGER.error("图片信息获取失败");
            throw new ServiceException("图片信息获取失败");
        }
        String filePath = imageInfo.getOrginPath();
        List<String> list = Lists.newArrayList();
        list.add(filePath);
        int result = fastDfsService.deleteFile(list);

        if (result == 0) {
            imageInfoService.deleteImageInfoByKLey(key);
        }

        return result;

    }

    /**
     * 通过url上传图片
     * @param url 图片的url
     * @return
     * @throws ServiceException
     */
    @Override
    public ImageResponse uploadImage(String url) throws ServiceException {
        if(StringUtils.isEmpty(url)){
            throw new ServiceException("图片url不能为空");
        }
        ImageInfo imageInfo=fastDfsService.uploadImage(url);
        ImageResponse imageResponse = getResponse(imageInfo,imageInfo.getImageSize());
        return imageResponse;
    }

    /**
     * 获得上传图片响应信息
     *
     * @param imageInfo 图片基本信息
     * @param size      图片大小
     * @return
     * @throws ServiceException
     */
    private ImageResponse getResponse(ImageInfo imageInfo, long size) throws ServiceException {
        if (imageInfo == null) {
            LOGGER.warn("上传图片失败");
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setValid(false);
            return imageResponse;
        }
        //保存图片信息
        imageInfoService.saveImageInfo(imageInfo);
        ImageResponse imageResponse = new ImageResponse(imageInfo.getOrginName(), imageInfo.getFileType(),
                size, imageInfo.getId());

        imageResponse.setValid(imageInfo.isValid() == 1 ? true : false);

        return imageResponse;

    }
}
