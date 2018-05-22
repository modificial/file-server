package com.codingapi.fileserver.service.impl;

import com.codingapi.fileserver.db.mysql.dao.ImageInfoDao;
import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import com.codingapi.fileserver.service.ImageInfoService;
import com.lorne.core.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author modificial
 * @date 2018/5/15 0015
 * @company codingApi
 * @description
 */
@Service
public class ImageInfoServiceImpl implements ImageInfoService {
    /**
     * 打印日志信息
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageInfoServiceImpl.class);

    @Autowired
    private ImageInfoDao imageInfoDao;

    /**
     * 查询图片信息
     *
     * @param key 图片文件访问秘钥
     * @return
     * @throws ServiceException
     */
    @Override
    public ImageInfo getImageInfoByKey(String key) throws ServiceException {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("文件访问秘钥不能为空");
            throw new ServiceException("文件访问秘钥不能为空");
        }
        return imageInfoDao.getImageInfoByKey(key);
    }

    /**
     * 保存图片信息
     *
     * @param imageInfo 图片文件信息
     * @return
     * @throws ServiceException
     */
    @Override
    public int saveImageInfo(ImageInfo imageInfo) throws ServiceException {
        if (imageInfo == null) {
            LOGGER.error("图片存储信息不能为空");
            throw new ServiceException("图片存储信息不能为空");
        }
        return imageInfoDao.insertImageInfo(imageInfo);
    }

    /**
     * 删除该图片信息
     *
     * @param key 图片文件秘钥
     * @return
     * @throws ServiceException
     */
    @Override
    public int deleteImageInfoByKLey(String key) throws ServiceException {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("文件访问秘钥不能为空");
            throw new ServiceException("文件访问秘钥不能为空");
        }

        return imageInfoDao.deleteImageInfoByKey(key);
    }
}
