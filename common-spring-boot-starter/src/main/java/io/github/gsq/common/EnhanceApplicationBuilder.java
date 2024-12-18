package io.github.gsq.common;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Project : cornerstone
 * Class : io.github.gsq.common.EnhanceApplicationBuilder
 *
 * @author : gsq
 * @date : 2024-10-29 15:52
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class EnhanceApplicationBuilder extends SpringApplicationBuilder {

    protected SpringApplication application;

    public EnhanceApplicationBuilder(Class<?>... sources) {
        super(sources);
        this.application = application();
        this.extendBeanPackage("io.github.gsq.common");
        super.banner((environment, sclass, out) -> {
            int i = RandomUtil.randomInt(1, 6);
            Banner banner = getTextBanner("classpath:banner/" + i + ".txt");
            if (banner != null) {
                banner.printBanner(environment, sclass, out);
            } else {
                out.println("你的banner走丢了");
            }
        });
    }

    @Deprecated
    public EnhanceApplicationBuilder extendBeanPackage(String packageName) {
        if (StrUtil.isEmpty(packageName)) {
            throw new IllegalArgumentException("预加载类路径不能为空");
        }
        Object proxy;
        String fliedName;
        Class<?> aClass = this.application.getMainApplicationClass();
        ComponentScan componentScan = aClass.getAnnotation(ComponentScan.class);
        if (componentScan == null) {
            SpringBootApplication springBootApplication = aClass.getAnnotation(SpringBootApplication.class);
            if (springBootApplication == null) {
                throw new IllegalArgumentException("请在启动类添加注解： " + SpringBootApplication.class);
            } else {
                proxy = springBootApplication;
                fliedName = "scanBasePackages";
            }
        } else {
            throw new RuntimeException("请不要在启动类中使用@ComponentScan注解");
        }
        try {
            InvocationHandler handler = Proxy.getInvocationHandler(proxy);
            Field value = handler.getClass().getDeclaredField("memberValues");
            value.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Object> memberValues = (Map<String, Object>) value.get(handler);
            String[] values = (String[]) memberValues.get(fliedName);
            if(ArrayUtil.isEmpty(values)) {
                values = ArrayUtil.append(values, aClass.getPackage().getName());
            }
            values = ArrayUtil.append(values, packageName);
            values = ArrayUtil.distinct(values);
            memberValues.put(fliedName, values);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("加载包路径{}失败：{}", packageName, e.getMessage(), e);
        }
        return this;
    }

    @Override
    protected SpringApplication createSpringApplication(ResourceLoader resourceLoader, Class<?>... sources) {
        return new EnhanceSpringApplication(resourceLoader, sources);
    }

    private Banner getTextBanner(String location) {
        Resource resource = new DefaultResourceLoader(ClassUtils.getDefaultClassLoader()).getResource(location);
        return resource.exists() ? new ResourceBanner(resource) : null;
    }

}
