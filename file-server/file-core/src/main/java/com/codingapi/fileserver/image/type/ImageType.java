package com.codingapi.fileserver.image.type;

import com.codingapi.netflix.framework.Constants;
import com.codingapi.netflix.framework.exception.ServerFeignException;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author modificial
 * @date 2018/5/10 0010
 * @company codingApi
 * @description
 */
public class ImageType {
    public static final String IMAGE_JPG = "jpg";
    public static final String IMAGE_JPEG = "jpeg";
    public static final String IMAGE_PNG = "png";
    public static final String IMAGE_GIF = "gif";
    public static final String IMAGE_IEF = "ief";
    public static final String IMAGE_PICT = "pict";
    public static final String IMAGE_TIF = "ttf";
    public static final String IMAGE_RAS = "ras";
    public static final String IMAGE_PNM = "pnm";
    public static final String IMAGE_PBM = "pbm";
    public static final String IMAGE_PGM = "pgm";
    public static final String IMAGE_PPM = "ppm";
    public static final String IMAGE_RGB = "rgb";
    public static final String IMAGE_XBM = "xbm";
    public static final String IMAGE_XPM = "xpm";
    public static final String IMAGE_XWD = "xwd";
    static Map<String, String> type = new ConcurrentHashMap<String, String>(16);

    private ImageType() {
    }

    static {
        type.put(IMAGE_GIF, "image/gif");
        type.put(IMAGE_JPG, "image/jpeg");
        type.put(IMAGE_JPEG, "image/jpeg");
        type.put(IMAGE_PBM, "image/x-portable-bitmap");
        type.put(IMAGE_PGM, "image/x-portable-graymap");
        type.put(IMAGE_PICT, "image/pict");
        type.put(IMAGE_PNG, "image/png");
        type.put(IMAGE_PNM, "image/x-portable-anymap");
        type.put(IMAGE_PPM, "image/x-portable-pixmap");
        type.put(IMAGE_RAS, "image/gif");
        type.put(IMAGE_RGB, "image/x-rgb");
        type.put(IMAGE_XBM, "image/x-xbitmap");
        type.put(IMAGE_XPM, "image/x-xpixmap");
        type.put(IMAGE_XWD, "image/x-xwindowdump");
        type.put(IMAGE_IEF, "image/ief ");
        type.put(IMAGE_TIF, "image/tiff");
    }

    /**
     * 根据后缀获取mine类型
     *
     * @param ext
     * @return
     * @throws ServerFeignException
     */
    public static final String getMineType(String ext) throws ServerFeignException {
        if (StringUtils.isEmpty(ext)) {
            throw new ServerFeignException(Constants.GLOBAL_EXCEPTION_DEFAULT_CODE, "后缀不能为空");
        }
        if (type.get(ext) == null) {
            throw new UnsupportedOperationException("不支持的类型");
        }
        return type.get(ext);
    }
}
