package io.github.gsq.hm.config;

import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.master.HmServer;
import io.github.gsq.hm.master.HostManager;
import io.github.gsq.hm.master.handler.Host;
import io.github.gsq.hm.master.handler.HostManagerImpl;
import io.github.gsq.hm.master.handler.MHeartbeatHandler;
import io.github.gsq.hm.master.handler.MLoginHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

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

    @Bean(name = "host_manager")
    public HostManager<Host> hostManager() {
        return new HostManagerImpl<Host>();
    }

    @Bean(name = "host_manager_server")
    public HmServer hmServer(@Qualifier("channel_initializer") ChannelInitializer<SocketChannel> initializer, MProperties properties) {
        return new HmServer(initializer, new InetSocketAddress(properties.getIp(), properties.getPort()));
    }

    @Bean(name = "channel_initializer")
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                ChannelPipeline cp = socketChannel.pipeline();
                cp.addLast(new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS));
                cp.addLast(new ProtobufVarint32FrameDecoder());
                cp.addLast(new ProtobufDecoder(Message.BaseMsg.getDefaultInstance()));
                cp.addLast(new ProtobufVarint32LengthFieldPrepender());
                cp.addLast(new ProtobufEncoder());
                cp.addLast(new MHeartbeatHandler(), new MLoginHandler());
            }
        };
    }

    protected static class MCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String isEnable = context.getEnvironment().getProperty("cornerstone.hm.master.enabled");
            return Boolean.parseBoolean(isEnable);
        }

    }

}
