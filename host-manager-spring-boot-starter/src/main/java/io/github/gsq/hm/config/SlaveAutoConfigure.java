package io.github.gsq.hm.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.config.SlaveAutoConfigure
 *
 * @author : gsq
 * @date : 2024-09-03 14:36
 * @note : It's not technology, it's art !
 **/
@Configuration
@Conditional(SlaveAutoConfigure.SCondition.class)
@EnableConfigurationProperties(SProperties.class)
public class SlaveAutoConfigure {



    protected static class SCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String mip = context.getEnvironment().getProperty("cornerstone.hm.master.ip");
            String sip = context.getEnvironment().getProperty("cornerstone.hm.slave.server");
            return StrUtil.isNotBlank(sip) && StrUtil.isBlank(mip);
        }

    }

}
