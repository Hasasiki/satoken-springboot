package com.ha.satoken.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class NoLoginExceptionHandler {
    @ExceptionHandler(value = NotLoginException.class)
    public SaResult handlerException(NotLoginException e) {
        log.error("need login in again---->{}", e.getMessage());
        SaResult saResult  = new SaResult();
        saResult.setCode(401);
        saResult.setMsg(e.getMessage());
        return saResult;
    }
}
