package com.nju.edu.erp.exception;

import com.nju.edu.erp.web.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.nju.edu.erp.web.controller"})
public class MyServiceExceptionHandler {
    @ExceptionHandler(MyServiceException.class)
    @ResponseBody
    private Response handleMyServiceException(MyServiceException e) {
        return new Response(e.getCode(), e.getMessage());
    }

}
