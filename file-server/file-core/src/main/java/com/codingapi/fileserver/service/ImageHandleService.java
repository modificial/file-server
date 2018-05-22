package com.codingapi.fileserver.service;

import com.lorne.core.framework.exception.ServiceException;

/**
 * @author modificial
 * @date 2018/4/23 0023
 * @company codingApi
 * @description 图片处理
 */
public interface ImageHandleService {
    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param input           输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     * @throws ServiceException 可能抛出业务异常
     */
    void imgNoScale(String input, String output, int width, int height, boolean keepAspectRatio) throws ServiceException;
}
