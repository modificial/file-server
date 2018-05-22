package com.codingapi.fileserver.image.impl;

import com.codingapi.fileserver.image.ImageWatermarkService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片水印
 */
@Service
public class ImageWatermarkServiceImpl implements ImageWatermarkService {
    /**
     * 加水印
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
    @Override
    public void imgWatermark(String source, String output, int width, int height, Position position, String watermark,
                             float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height)
                    .watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f)
                    .toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
