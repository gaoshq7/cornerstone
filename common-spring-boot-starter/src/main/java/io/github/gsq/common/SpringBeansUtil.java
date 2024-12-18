package io.github.gsq.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project : cornerstone
 * Class : io.github.gsq.common.SpringBeansUtil
 *
 * @author : gsq
 * @date : 2024-10-30 15:49
 * @note : It's not technology, it's art !
 **/
@Slf4j
@Configuration
public class SpringBeansUtil implements ApplicationContextAware {

    private static volatile ApplicationContext context;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        SpringBeansUtil.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return SpringBeansUtil.context;
    }

    public static <T> T getBean(Class<T> c) {
        T result = null;
        try {
            result = context.getBean(c);
        } catch (NoSuchBeanDefinitionException exception) {
//            log.error("按类型获取Bean失败：", exception);
        }
        return result;
    }

    public static <T> T getBean(String name) {
        T result = null;
        try {
            result = (T) context.getBean(name);
        } catch (NoSuchBeanDefinitionException exception) {
//            log.error("按名称获取Bean失败：", exception);
        }
        return result;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        T result = null;
        try {
            result = context.getBean(name, clazz);
        } catch (NoSuchBeanDefinitionException exception) {
//            log.error("按名称及类型获取Bean失败：", exception);
        }
        return result;
    }

    public static <T> List<T> getBeans(Class<T> clazz){
        List<T> result = null;
        try {
            Map<String, T> map = context.getBeansOfType(clazz);
            result = map.keySet().stream().map(map::get).collect(Collectors.toList());
        } catch (NoSuchBeanDefinitionException exception) {
//            log.error("按类型获取Bean集合失败：", exception);
        }
        return result;
    }

    public static <T> Collection<T> getBeansByAnnotation(Class<? extends Annotation> clazz) {
        Map<String, Object> beanWhithAnnotation = context.getBeansWithAnnotation(clazz);
        Set<Map.Entry<String, Object>> entitySet = beanWhithAnnotation.entrySet();
        Set<T> result = CollUtil.newHashSet();
        for (Map.Entry<String, Object> entry : entitySet) {
            Class<? extends T> aClass = (Class<? extends T>) entry.getValue().getClass();
            CollUtil.addAll(result, getBeans(aClass));
        }
        return result;
    }

    public static <T> T registerSingleton(Class<T> tClass) {
        Objects.requireNonNull(tClass);
        AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
        T obj = autowireCapableBeanFactory.createBean(tClass);
        String beanName = StrUtil.upperFirst(tClass.getSimpleName());
        registerSingleton(beanName, obj);
        return obj;
    }

    public static int registerSingleton(String beanName, Object object) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
        configurableListableBeanFactory.registerSingleton(beanName, object);
        return configurableListableBeanFactory.getSingletonCount();
    }

    public static <T> void registerBean(String beanName, Class<T> beanClass, Object ... constructorArgs) {
        if (Objects.isNull(beanClass) || StrUtil.isBlank(beanName)) {
            throw new IllegalArgumentException("Bean注册名称及注册类型不可为null");
        }
        if (!ObjectUtil.isNull(getBean(beanName))) {
            throw new RuntimeException(beanName + "在环境中已经存在，无法注册");
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        Optional.ofNullable(constructorArgs).ifPresent(argArr ->
                Arrays.stream(argArr).forEach(builder::addConstructorArgValue));
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        factory.registerBeanDefinition(beanName, beanDefinition);
    }

    public static void removeBeanByName(String name) {
        Object o = getBean(name);
        if(ObjectUtil.isNotNull(o)) {
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
//            factory.destroyScopedBean(name);
            factory.removeBeanDefinition(name);
        }
    }

    public static <T> void removeBeanByClass(Class<T> clazz) {
        List<T> beans = getBeans(clazz);
        if(CollUtil.isNotEmpty(beans)) {
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
            String[] beanNames = factory.getBeanDefinitionNames();
            Map<Object, String> beansMap = MapUtil.newHashMap();
            for (String beanName : beanNames) {
                Object bean = factory.getBean(beanName);
                beansMap.put(bean, beanName);
            }
            for (T bean : beans) {
                if (beansMap.containsKey(bean)) {
//                    factory.destroyScopedBean(beansMap.get(bean));
                    factory.removeBeanDefinition(beansMap.get(bean));
                }
            }
        }
    }

}
