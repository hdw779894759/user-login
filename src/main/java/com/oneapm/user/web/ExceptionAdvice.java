package com.oneapm.user.web;

import com.oneapm.user.exception.IllegalEncryptedTextException;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;

import static com.alibaba.fastjson.JSON.toJSONString;

@ControllerAdvice
@Log
public class ExceptionAdvice {

    @ExceptionHandler(IllegalEncryptedTextException.class)
    public ResponseEntity<?> handleEncryptedError(IllegalEncryptedTextException text) {
        log.log(Level.INFO, toJSONString(text));

        return ResponseEntity.ok(false);
    }
}
