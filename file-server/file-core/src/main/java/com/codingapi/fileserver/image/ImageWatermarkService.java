package com.codingapi.fileserver.image;


import net.coobird.thumbnailator.geometry.Position;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片加水印
 */
public interface ImageWatermarkService {
    /**
     * 水印
     *
     * @param source       输入源
     * @param output       输入源
     * @param width        宽
     * @param height       高
     * @param position     水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark    水印图片地址
     * @param transparency 透明度 0.5f
     * @param quality      图片质量 0.8f
     */
    void imgWatermark(String source, String output, int width, int height, Position position, String watermark,
                      float transparency, float quality);
}
