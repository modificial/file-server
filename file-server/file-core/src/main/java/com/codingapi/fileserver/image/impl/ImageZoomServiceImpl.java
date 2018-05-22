package com.codingapi.fileserver.image.impl;

import com.codingapi.fileserver.image.ImageZoomService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片缩放
 */
@Service
public class ImageZoomServiceImpl implements ImageZoomService {
    /**
     * 压缩图片
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    @Override
    public void imgThumb(String source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 制定压缩的比例
     *
     * @param source 输入源
     * @param output 输出源
     * @param scale  比例
     */
    @Override
    public void imgScale(String source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param source          输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    @Override
    public void imgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OutputStream imgOutputStream(String source, int width, int height) {
        OutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            Thumbnails.of(source).scale(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return os;
    }
}


