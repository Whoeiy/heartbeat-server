package com.example.heartbeatserver.util;

import org.springframework.beans.BeanUtils;

public abstract class BeanUtil {
    public static Object copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source == null) {
            return target;
        }
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }
}
