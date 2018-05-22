package com.codingapi.fileserver.image.impl;

import com.codingapi.fileserver.image.ImageFormatService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片格式转换
 */
@Service
public class ImageFormatServiceImpl implements ImageFormatService {

    /**
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     */
    @Override
    public void imgFormat(String source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     * @return
     */
    @Override
    public BufferedImage imgBufferedImage(String source, String output, int width, int height, String format) {
        BufferedImage buff = null;
        try {
            buff = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buff, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buff;
    }
}