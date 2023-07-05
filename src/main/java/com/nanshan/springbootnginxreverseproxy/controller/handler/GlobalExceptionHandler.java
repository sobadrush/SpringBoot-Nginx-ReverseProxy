package com.nanshan.springbootnginxreverseproxy.controller.handler;

import com.nanshan.springbootnginxreverseproxy.exception.MySystemException;
import com.nanshan.springbootnginxreverseproxy.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

/**
 * 全域異常處理，捕獲所有 Controller 中拋出的異常
 * <p>
 * ref. https://www.jianshu.com/p/12e1a752974d
 */
@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice // 也是 @Component
// @RestControllerAdvice // @ControllerAdvice + @ResponseBody + @Component
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private Environment springEnv;

    // 處理自訂義的異常
    // 可根據 e.getCode() 的狀態碼進行針對性錯誤處理
    @ExceptionHandler(MySystemException.class)
    @ResponseBody
    public ApiResult customHandler(MySystemException e) {
        log.error("GlobalExceptionHandler.customHandler: " + e.getMessage(), e);

        String actProfile = Arrays.stream(springEnv.getActiveProfiles()).findFirst().get();
        switch (e.getCode()) {
            case "503":
                log.info("[" + actProfile + "]" + "[判斷錯誤代碼] 發生 503 錯誤");
                break;
            case "507":
                log.info("[" + actProfile + "]" + "[判斷錯誤代碼] 發生 507 錯誤");
                break;
        }

        return ApiResult.builder()
                .errorCode(Integer.parseInt(e.getCode()))
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler({ BindException.class })
    public String handleBindException(Throwable e, RedirectAttributes redirectAttributes) {
        BindingResult results = ((BindException) e).getBindingResult();
        // 取得第一個例外訊息
        String firstErrMsg = results.getFieldErrors().get(0).getDefaultMessage();
        redirectAttributes.addFlashAttribute("ERR_MSG", firstErrMsg);
        redirectAttributes.addFlashAttribute("MESSAGE", "我是 Flash Attribute");

        // 取得全部例外訊息
        for(FieldError fieldErr : results.getFieldErrors()) {
        	System.err.println(fieldErr.getField() + " - " + fieldErr.getDefaultMessage());
        }

        return "redirect:/index.jsp";
    }

    // 其他未處理異常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        log.error("GlobalExceptionHandler.exceptionHandler: " + e.getMessage(), e);
        return ApiResult.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage("系統錯誤: " + e.getMessage())
                .build();
    }

}