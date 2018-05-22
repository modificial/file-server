package com.codingapi.fileserver.api.interceptor.annocation;

import java.lang.annotation.*;

/**
 * @author modificial
 * @date 2018/4/20 0020
 * @company codingApi
 * @description
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenRequired {
    /**
     * 是否需要令牌验证
     */
    boolean required() default true;
}
