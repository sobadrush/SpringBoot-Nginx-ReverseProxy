package com.nanshan.springbootnginxreverseproxy.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author RogerLo
 * @date 2023/7/10
 *
 * ref. https://juejin.cn/post/7097937334721183757
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    public static ApplicationContext context;

    public void setApplicationContext(ApplicationContext appContext) {
        if (appContext == null) {
            context = appContext;
        }
    }

    public static Object getBean(String beanId) {
        return context.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            return null;
        }
        return (T) context.getBean(clazz);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) {
        if (context == null) {
            return null;
        }
        return (T) context.getBean(beanId, clazz);
    }

    public static Boolean containsBean(String beanId) {
        if (context == null) {
            return null;
        }
        return context.containsBean(beanId);
    }

    public static Boolean isSingleton(String beanId) {
        return context.isSingleton(beanId);
    }

    public static Class<? extends Object> getType(String beanId) {
        return context.getType(beanId);
    }

}
