package com.bjpowernode.crm.settings.handler;

import com.bjpowernode.crm.settings.exception.ActOrPwdException;
import com.bjpowernode.crm.settings.exception.ActTimeOutException;
import com.bjpowernode.crm.settings.exception.IpRestrictedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ActOrPwdException.class)
    public String doActOrPwdException(Exception exception) throws JsonProcessingException {
        String msg = exception.getMessage();
        Map<String,String> map = new HashMap<>();
        map.put("succeed","false");
        map.put("msg",msg);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(map));
        return objectMapper.writeValueAsString(map);
    }
    @ExceptionHandler(value = IpRestrictedException.class)
    public String doIpRestrictedException(Exception exception) throws JsonProcessingException {
        String msg = exception.getMessage();
        Map<String,String> map = new HashMap<>();
        map.put("succeed","false");
        map.put("msg",msg);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(map));
        return objectMapper.writeValueAsString(map);
    }
    @ExceptionHandler(value = ActTimeOutException.class)
    public String doActTimeOutException(Exception exception) throws JsonProcessingException {
        String msg = exception.getMessage();
        Map<String,String> map = new HashMap<>();
        map.put("succeed","false");
        map.put("msg",msg);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(map));
        return objectMapper.writeValueAsString(map);
    }
}
