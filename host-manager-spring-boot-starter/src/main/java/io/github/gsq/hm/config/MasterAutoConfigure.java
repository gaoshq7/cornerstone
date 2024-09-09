package io.github.gsq.hm.config;

import cn.hutool.core.util.StrUtil;
import io.github.gsq.hm.master.HmServer;
import io.github.gsq.hm.master.HostManager;
import io.github.gsq.hm.master.HostManagerImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.config.MasterAutoConfigure
 *
 * @author : gsq
 * @date : 2024-09-03 14:36
 * @note : It's not technology, it's art !
 **/
@Configuration
@Conditional(MasterAutoConfigure.MCondition.class)
@EnableConfigurationProperties(MProperties.class)
public class MasterAutoConfigure {

    @Bean(name = "host_manager_server")
    public HmServer hmServer() {
        return new HmServer();
    }

    @Bean(name = "host_manager")
    public HostManager hostManager() {
        return new HostManagerImpl();
    }

    protected static class MCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String mip = context.getEnvironment().getProperty("cornerstone.hm.master.ip");
            String sip = context.getEnvironment().getProperty("cornerstone.hm.slave.server");
            return StrUtil.isNotBlank(mip) && StrUtil.isBlank(sip);
        }

    }

}
