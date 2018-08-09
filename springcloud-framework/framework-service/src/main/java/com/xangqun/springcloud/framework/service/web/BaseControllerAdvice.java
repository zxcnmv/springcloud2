/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.web;


import com.xangqun.springcloud.framework.service.exception.BaseException;
import com.xangqun.springcloud.framework.service.util.I18nUtils;
import com.xangqun.springcloud.framework.service.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author laixiangqun
 * @since 2018-7-20
 */
@ControllerAdvice
public class BaseControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(BaseControllerAdvice.class);


    private static final String CONTEXT_ATTRIBUTE = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
    /**
     * 用于处理BaseException异常
     */
    @ExceptionHandler({BaseException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected Object baseException(BaseException baseException, HttpServletRequest request,
                               HttpServletResponse response) {
        logger.error("got a Exception", baseException);
        String message = "";
        String exceptionCode, uri = request.getRequestURI();
        exceptionCode = baseException.getCode();
        Object[] args = baseException.getValues();
        message = message
                + I18nUtils.getMessage(exceptionCode, (String[]) args, baseException.getMessage(), request);

        baseException.setMessage(message);

        Object result = message;
        if (StringUtils.contains(uri, "/api/")) {
            result = MessageUtils.getResponseByMessage(message);
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }

    /**
     * 用于处理MethodArgumentNotValidException异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected Object methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request,
                               HttpServletResponse response) {
        logger.error("got a Exception", exception);
        String message = "";
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext context =
                (WebApplicationContext) servletContext.getAttribute(CONTEXT_ATTRIBUTE);

        String exceptionCode = "", uri = request.getRequestURI();

        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        if (null != errors && !errors.isEmpty()) {
            exceptionCode = errors.get(0).getDefaultMessage();
        }
        message = message
                + context.getMessage(exceptionCode, null, exception.getMessage(), request.getLocale());

        Object result = message;
        if (StringUtils.contains(uri, "/api/")) {
            result = MessageUtils.getResponseByMessage(message);
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }

    /**
     * 用于处理通用异常
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected Object exception(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        logger.error("got a Exception", exception);
        String message = "";
        String uri = request.getRequestURI();
        logger.error(exception.getMessage(), exception);
        message = message + "系统内部错误！";
        Object result = message;
        if (StringUtils.contains(uri, "/api/")) {
            result = MessageUtils.getResponseByMessage(message);
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }
}
