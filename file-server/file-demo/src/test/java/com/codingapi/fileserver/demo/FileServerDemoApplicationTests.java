package com.codingapi.fileserver.demo;

import com.codingapi.fileserver.db.fastdfs.utils.SnowFlakeGenerator;
import com.codingapi.fileserver.db.mysql.dao.FileInfoDao;
import com.codingapi.fileserver.db.mysql.dao.ImageInfoDao;
import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FileServerDemoApplicationTests {
    @Autowired
    private ImageInfoDao imageInfoDao;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Test
    public void getInfo() {
        String key = "445879614371385344";
        System.out.println(fileInfoDao.getFileInfoByKey(key));
    }

    @Test
    public void insert() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setBlockPath("sksksk");
        fileInfo.setFileSize(12555222);
        fileInfo.setFileType("skksksd");
        fileInfo.setId(SnowFlakeGenerator.nextId());
        fileInfo.setMd5FileStr("ksksdkd");
        fileInfo.setOrginFileName("dddd");
        fileInfo.setUploadTime(new Date());
        fileInfo.setTempPath("dlldld");
        fileInfoDao.insertFileInfo(fileInfo);

    }

    @Test
    public void delete() {
        String key = "445879614371385344";
        System.out.println(fileInfoDao.deleteFileInfoByKey(key));
    }

    @Test
    public void getInfo1() {
        String key = "445881825239355392";
        System.out.println(imageInfoDao.getImageInfoByKey(key));
    }

    @Test
    public void insert1() {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(SnowFlakeGenerator.nextId());
        imageInfo.setFileType("dsdd");
        imageInfo.setImageSize(2555);
        imageInfo.setOrginName("sdddd");
        imageInfo.setOrginPath("dsdd");
        imageInfo.setUploadTime(new Date());
        imageInfo.setValid(1);
        imageInfoDao.insertImageInfo(imageInfo);

    }

    @Test
    public void delete1() {
        String key = "445881825239355392";
        System.out.println(imageInfoDao.deleteImageInfoByKey(key));
    }
}
