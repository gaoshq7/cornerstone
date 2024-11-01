package io.github.gsq.hm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.config.MProperties
 *
 * @author : gsq
 * @date : 2024-09-03 16:23
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "cornerstone.hm.master")
public class MProperties {

    private boolean enabled = false;

    private boolean debug = false;

    private String ip = "0.0.0.0";

    private Integer port = 19999;

}
