package com.codingapi.fileserver.api.service;

import com.codingapi.fileserver.ato.ao.ImageParam;
import com.codingapi.fileserver.ato.vo.ImageResponse;
import com.lorne.core.framework.exception.ServiceException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

/**
 * @author modificial
 * @date 2018/4/24
 * @company codingApi
 * @description
 */
public interface ApiImageService {
    /**
     * 上传图片
     *
     * @param imageParam
     * @return
     * @throws ServiceException 可能抛出业务异常
     */
    ImageResponse upload(ImageParam imageParam) throws ServiceException;

    /**
     * 下载图片
     *
     * @param token  令牌
     * @param key    访问秘钥
     * @param width  图片宽度
     * @param height 图片高度
     * @return
     * @throws ServiceException
     */
    ResponseEntity<InputStreamResource> download(String token, String key, Integer width, Integer height) throws ServiceException;

    /**
     * 根据key删除图片
     *
     * @param key
     * @return
     * @throws ServiceException
     */
    int delete(String key) throws ServiceException;

    /**
     * 通过url上传图片
     * @param url 图片的url
     * @return
     */
    ImageResponse uploadImage(String url) throws ServiceException;
}
