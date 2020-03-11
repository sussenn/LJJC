package com.xinghuo.ljjc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName BaseExceptionHandle   全局异常处理器
 * @Author sussen
 * @Version 1.0
 * @Data 2020/3/5
 */
@RestControllerAdvice
@Slf4j
public class BaseExceptionHandle {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error("程序异常", e);
    }
}
