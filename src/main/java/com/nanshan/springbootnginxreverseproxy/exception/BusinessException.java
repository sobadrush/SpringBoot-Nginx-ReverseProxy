package com.nanshan.springbootnginxreverseproxy.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author RogerLo
 * @date 2023/7/4
 */
@ResponseStatus(reason = "我是使用 @ResponseStatus 的自訂 BusinessException")
public class BusinessException extends RuntimeException {
}