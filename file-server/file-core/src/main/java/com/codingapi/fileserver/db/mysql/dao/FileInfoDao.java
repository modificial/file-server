package com.codingapi.fileserver.db.mysql.dao;

import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author modificial
 * @date 2018/5/14
 * @company codingApi
 * @description 文件信息存储
 */
@Repository
public interface FileInfoDao {

    FileInfo getFileInfoByKey(@Param("id") String key);

    int deleteFileInfoByKey(@Param("id") String key);

    int insertFileInfo(FileInfo fileInfo);

    int updateFileInfo(FileInfo fileInfo);

}
