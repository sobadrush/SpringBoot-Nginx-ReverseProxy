package com.nanshan.springbootnginxreverseproxy.exception;

/**
 * 自訂義異常類別
 * 用來進行 Controller 發生錯誤時，對 ControllerAdvice 的狀態碼傳遞
 */
public class MySystemException extends RuntimeException {

    private String code; // 狀態碼

    public MySystemException() {
    }

    public MySystemException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}