package com.codingapi.fileserver.service.impl;

import com.codingapi.fileserver.image.ImageZoomService;
import com.codingapi.fileserver.service.ImageHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author modificial
 * @date 2018/4/23 0023
 * @company codingApi
 * @description 图片压缩处理
 */
@Service
public class ImageHandleServiceImpl implements ImageHandleService {
    @Autowired
    private ImageZoomService imageZoomService;

    /**
     * 压缩图片可以选择是否按比例
     *
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false时不按比例缩放
     */
    @Override
    public void imgNoScale(String input, String output, int width, int height, boolean keepAspectRatio) {
        imageZoomService.imgNoScale(input, output, width, height, keepAspectRatio);
    }

}
