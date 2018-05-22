package com.codingapi.fileserver.service;

import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import com.lorne.core.framework.exception.ServiceException;

/**
 * @author modificial
 * @date 2018/5/15 0015
 * @company codingApi
 * @description 图片上传存储
 */
public interface ImageInfoService {
    /**
     * 获取存储的图片信息
     *
     * @param key 图片文件访问秘钥
     * @return
     */
    ImageInfo getImageInfoByKey(String key) throws ServiceException;

    /**
     * 保存图片信息
     *
     * @param imageInfo 图片文件信息
     * @return
     */
    int saveImageInfo(ImageInfo imageInfo) throws ServiceException;

    /**
     * 删除图片文件信息
     *
     * @param key 图片文件秘钥
     * @return
     */
    int deleteImageInfoByKLey(String key) throws ServiceException;

}
