package io.github.gsq.hm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.config.SProperties
 *
 * @author : gsq
 * @date : 2024-09-03 16:23
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "cornerstone.hm.slave")
public class SProperties {

    private boolean enabled = false;

    private String server = "127.0.0.1";

    private Integer port = 19999;

}
