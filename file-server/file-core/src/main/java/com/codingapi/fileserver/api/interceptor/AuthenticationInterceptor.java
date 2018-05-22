package com.codingapi.fileserver.api.interceptor;

import com.codingapi.fileserver.api.interceptor.annocation.TokenRequired;
import com.codingapi.fileserver.ato.ao.FileConfig;
import com.codingapi.sso.client.api.SSOTokenClient;
import com.codingapi.sso.client.ato.vo.SSOUser;
import com.lorne.core.framework.exception.ServiceException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author modificial
 * @date 2018/4/20 0020
 * @company codingApi
 * @description
 **/
@Configuration
@EnableConfigurationProperties(FileConfig.class)
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private FileConfig fileConfig;

    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    /**
     * 当前用户
     */
    private static final String CURRENT_USER = "currentUser";
    /**
     * 注入sso验证中心
     */
    @Autowired
    private SSOTokenClient ssoClient;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        TokenRequired tokenRequired = method.getDeclaredAnnotation(TokenRequired.class);
        if (tokenRequired != null && tokenRequired.required() == true) {
            String token = request.getParameter("token");
            if (token.equals(request.getSession().getAttribute(CURRENT_USER))) {
                return true;
            }
            if (!StringUtils.hasText(token)) {
                LOGGER.debug("token值为空");
                throw new ServiceException("token的值不能为空");
            }
            LOGGER.info("开始获取token");
            SSOUser ssoUser = ssoClient.verify(token);
            if (ssoUser != null) {
                request.getSession().setAttribute(CURRENT_USER, ssoUser);
                LOGGER.debug("获取token成功");
                return true;
            }
            return false;
        }
        LOGGER.warn("获取token信息失败");

        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView
            modelAndView) {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     */
    @Override
    @Async
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LOGGER.debug("调用方法完毕");

        LOGGER.info(fileConfig.getTempdir());

        File file = new File(fileConfig.getTempdir());

        if (file.length() > fileConfig.getMaxsize()) {

            LOGGER.debug("并开始执行临时文件清理工作");

            File[] files = file.listFiles();
            for (File f : files) {
                try {
                    FileUtils.forceDelete(f);

                } catch (IOException e1) {
                    LOGGER.warn("文件被占用无法删除");
                }
            }
        }
    }
}
