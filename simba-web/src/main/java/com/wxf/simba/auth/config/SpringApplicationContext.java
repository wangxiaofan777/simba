package com.wxf.simba.auth.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * Spring上下文
 *
 * @author WangMaoSong
 * @since 2023-01-02 20:48
 **/
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static Object getBean(String name, Object... args) throws BeansException {
        return applicationContext.getBean(name, args);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(Class<T> clazz, Object... args) {
        return applicationContext.getBean(clazz, args);
    }


    public static void publishEvent(Object object) {
        applicationContext.publishEvent(object);
    }


    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}
