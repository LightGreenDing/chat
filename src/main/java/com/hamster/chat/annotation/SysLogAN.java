package com.hamster.chat.annotation;

import java.lang.annotation.*;

/**
 * 操作日志记录
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogAN {
    String value() default "";
}

