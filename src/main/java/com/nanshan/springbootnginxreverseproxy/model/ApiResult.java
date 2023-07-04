package com.nanshan.springbootnginxreverseproxy.model;

import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@Data
public class ApiResult<T> {

    private T result;
    private boolean success;
    private int errorCode;
    private String errorMessage;
    private String errorDetail;

    public ApiResult() {
    }

    public ApiResult(T tt) {
        this.result = tt;
    }

    public ApiResult(T result, Exception ex) {
        this.result = result;
        this.success = false;
        this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errorMessage = ExceptionUtils.getRootCauseMessage(ex);
        this.errorDetail = String.valueOf(ExceptionUtils.getRootCause(ex));
    }

}