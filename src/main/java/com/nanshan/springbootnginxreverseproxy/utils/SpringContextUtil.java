package com.nanshan.springbootnginxreverseproxy.utils;

import org.springframework.beans.BeansException;
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

    public static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (appContext == null) {
            appContext = context;
        }
    }

    public static Object getBean(String beanId) {
        return appContext.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz) {
        if (appContext == null) {
            return null;
        }
        return (T) appContext.getBean(clazz);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) {
        if (appContext == null) {
            return null;
        }
        return (T) appContext.getBean(beanId, clazz);
    }

    public static Boolean containsBean(String beanId) {
        if (appContext == null) {
            return null;
        }
        return appContext.containsBean(beanId);
    }

    public static Boolean isSingleton(String beanId) {
        return appContext.isSingleton(beanId);
    }

    public static Class<? extends Object> getType(String beanId) {
        return appContext.getType(beanId);
    }

}
