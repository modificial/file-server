package com.codingapi.fileserver.api.service.impl;

/**
 * @author modificial
 * @date 2018/4/25 0025
 * @company codingApi
 * @description
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author chenssy
 * @project: Test
 * @date 2013-7-28
 * @Description: 文件压缩工具类
 * 将指定文件/文件夹压缩成zip、rar压缩文件
 */
public class CompressedFileUtil {
    /**
     * 默认构造函数
     */
    public CompressedFileUtil() {

    }

    /**
     * @param resourcesPath 源文件/文件夹
     * @param targetPath    目的压缩文件保存路径
     * @return void
     * @throws Exception
     * @desc 将源文件/文件夹生成指定格式的压缩文件,格式zip
     */
    public void compressedFile(String resourcesPath, String targetPath) throws Exception {
        File resourcesFile = new File(resourcesPath);     //源文件
        File targetFile = new File(targetPath);           //目的
        //如果目的路径不存在，则新建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String targetName = resourcesFile.getName() + ".zip";   //目的压缩文件名
        FileOutputStream outputStream = new FileOutputStream(targetPath + "\\" + targetName);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));

        createCompressedFile(out, resourcesFile, "");

        out.close();
    }

    /**
     * @param out  输出流
     * @param file 目标文件
     * @return void
     * @throws Exception
     * @desc 生成压缩文件。
     * 如果是文件夹，则使用递归，进行文件遍历、压缩
     * 如果是文件，直接压缩
     */
    public void createCompressedFile(ZipOutputStream out, File file, String dir) throws Exception {
        //如果当前的是文件夹，则进行进一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir + "/"));

            dir = dir.length() == 0 ? "" : dir + "/";

            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                createCompressedFile(out, files[i], dir + files[i].getName());         //递归处理
            }
        } else {   //当前的是文件，打包处理
            //文件输入流
            FileInputStream fis = new FileInputStream(file);

            out.putNextEntry(new ZipEntry(dir));
            //进行写操作
            int j = 0;
            byte[] buffer = new byte[1024];
            while ((j = fis.read(buffer)) > 0) {
                out.write(buffer, 0, j);
            }
            //关闭输入流
            fis.close();
        }
    }

    public static void main(String[] args) {
        CompressedFileUtil compressedFileUtil = new CompressedFileUtil();
        try {
            compressedFileUtil.compressedFile("G:\\zip", "F:\\zip");
            System.out.println("压缩文件已经生成...");
        } catch (Exception e) {
            System.out.println("压缩文件生成失败...");
            e.printStackTrace();
        }
    }
}
