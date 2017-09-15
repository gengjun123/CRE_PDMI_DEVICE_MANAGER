package com.dayang.deviceManager.controller;


import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

public class BaseController {

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(Exception e) {
        Logger logger = Logger.getLogger(BaseController.class);
        logger.error("服务器运行错误", e);
        return ResponseEntity.status(500).body(Collections.singletonMap("info", "服务器内部错误"));
    }
}
