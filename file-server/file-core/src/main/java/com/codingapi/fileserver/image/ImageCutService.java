package com.codingapi.fileserver.image;


import net.coobird.thumbnailator.geometry.Position;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片裁剪
 */
public interface ImageCutService {

    /**
     * 裁剪图片
     *
     * @param source          输入源
     * @param output          输出源
     * @param position        裁剪位置
     * @param x               裁剪区域x
     * @param y               裁剪区域y
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    void imgSourceRegion(String source, String output, Position position, int x, int y,
                         int width, int height,
                         boolean keepAspectRatio);

    /**
     * 按坐标裁剪
     *
     * @param source          输入源
     * @param output          输出源
     * @param x               起始x坐标
     * @param y               起始y坐标
     * @param x1              结束x坐标
     * @param y1              结束y坐标
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    void imgSourceRegion(String source, String output, int x, int y, int x1, int y1, int
            width, int height,
                         boolean keepAspectRatio);
}