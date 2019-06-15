package com.hamster.chat.exception;

import com.hamster.chat.base.ResponeResult;
import com.hamster.chat.enums.Enum_Response_Code;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponeResult errorHandler(Exception ex) {
        ResponeResult result = new ResponeResult();
        result.setCode(Enum_Response_Code.系统错误.code);
        result.setMsg(Enum_Response_Code.系统错误.desc);
        return result;
    }

    /**
     * 拦截捕捉自定义异常 CustomizeException.class
     *
     * @param ex 自定义异常
     * @return
     */
    @ExceptionHandler(value = CustomizeException.class)
    public ResponeResult myErrorHandler(CustomizeException ex) {
        ResponeResult result = new ResponeResult();
        result.setCode(ex.getCode());
        result.setMsg(ex.getMsg());
        return result;
    }

}
