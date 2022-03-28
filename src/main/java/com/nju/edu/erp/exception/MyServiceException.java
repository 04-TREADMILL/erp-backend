package com.nju.edu.erp.exception;

import lombok.Data;

/**
 * @author mars
 */
@Data
public class MyServiceException extends RuntimeException {
    private String code;

    public MyServiceException(String message, Throwable root) {
        super(message, root);
    }

    public MyServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
