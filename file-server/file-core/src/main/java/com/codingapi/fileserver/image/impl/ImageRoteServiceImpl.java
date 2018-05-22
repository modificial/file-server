package com.codingapi.fileserver.image.impl;


import com.codingapi.fileserver.image.ImageRoteService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片旋转
 */
@Service
public class ImageRoteServiceImpl implements ImageRoteService {
    /**
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param rotate 角度,正数顺时针  负数逆时针
     */
    @Override
    public void imgRotate(String source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
