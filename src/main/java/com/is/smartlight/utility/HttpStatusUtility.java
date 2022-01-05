package com.is.smartlight.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HttpStatusUtility extends ResponseEntityExceptionHandler {

    public static ResponseEntity<?> successResponse() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<?> successResponse(String objName, Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(objName, obj);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static ResponseEntity<?> successResponse(Object obj) {
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    public static ResponseEntity<?> createdResponse() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static ResponseEntity<?> createdResponse(String objName, Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(objName, obj);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> createdResponse(Object obj) {
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<Map<String, String>> handleHttpStatusException(UndeclaredThrowableException e) {
        Throwable ex = e.getCause();

        ex.printStackTrace();

        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, String>> handleHttpStatusException(Throwable e) {
        e.printStackTrace();

        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
