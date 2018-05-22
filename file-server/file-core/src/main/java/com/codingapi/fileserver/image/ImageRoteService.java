package com.codingapi.fileserver.image;

/**
 * @author modificial
 * @date 2018/4/18 0018
 * @company codingApi
 * @description 图片旋转
 */
public interface ImageRoteService {
    /**
     * 旋转 ,正数：顺时针 负数：逆时针
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param rotate 角度,正数顺时针  负数逆时针
     */
    void imgRotate(String source, String output, int width, int height, double rotate);
}