package com.codingapi.fileserver.image;


import java.awt.image.BufferedImage;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片格式转换
 */
public interface ImageFormatService {

    /**
     * 转化图像格式
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     */
    void imgFormat(String source, String output, int width, int height, String format);

    /**
     * 输出到BufferedImage
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     * @return BufferedImage
     */
    BufferedImage imgBufferedImage(String source, String output, int width, int height, String format);

}
