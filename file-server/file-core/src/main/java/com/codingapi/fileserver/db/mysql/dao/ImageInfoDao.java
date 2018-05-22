package com.codingapi.fileserver.db.mysql.dao;

import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author modificial
 * @date 2018/5/14
 * @company codingApi
 * @description 图片信息存储
 */
@Repository
public interface ImageInfoDao {

    ImageInfo getImageInfoByKey(@Param("id") String key);

    int insertImageInfo(ImageInfo imageInfo);

    int deleteImageInfoByKey(@Param("id") String key);
}
