package io.github.gsq.common;

import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ResourceLoader;

/**
 * Project : cornerstone
 * Class : io.github.gsq.common.EnhanceSpringApplication
 *
 * @author : gsq
 * @date : 2024-10-29 16:43
 * @note : It's not technology, it's art !
 **/
public class EnhanceSpringApplication extends SpringApplication {

    protected EnhanceSpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        super(resourceLoader, primarySources);
    }

}
