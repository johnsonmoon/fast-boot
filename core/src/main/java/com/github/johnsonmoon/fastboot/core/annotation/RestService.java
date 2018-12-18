package com.github.johnsonmoon.fastboot.core.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Create by johnsonmoon at 2018/12/17 16:41.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RestService {
}