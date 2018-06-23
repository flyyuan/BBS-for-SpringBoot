package com.bennyshi.demo.ann;

import java.lang.annotation.*;

/**
 * 在需要登录验证的Controller的方法上使用此注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}