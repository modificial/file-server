package com.codingapi.fileserver.image;

import java.io.OutputStream;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片缩放
 */
public interface ImageZoomService {
    /**
     * 指定大小进行缩放 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    void imgThumb(String source, String output, int width, int height);

    /**
     * 按照比例进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param scale  比例
     */
    void imgScale(String source, String output, double scale);

    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param source          输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    void imgNoScale(String source, String output, int width, int height, boolean keepAspectRatio);

    /**
     * 输出到OutputStream
     *
     * @param source 输入源
     * @param width  宽
     * @param height 高
     * @return toOutputStream(流对象)
     */
    OutputStream imgOutputStream(String source, int width, int height);
}

