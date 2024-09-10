package io.github.gsq.hm.config;

import cn.hutool.core.util.StrUtil;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.master.HmServer;
import io.github.gsq.hm.master.HostManager;
import io.github.gsq.hm.master.HostManagerImpl;
import io.github.gsq.hm.master.handler.MHeartbeatHandler;
import io.github.gsq.hm.master.handler.MLoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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
    public HostManager hostManager() {
        return new HostManagerImpl();
    }

    @Bean(name = "host_manager_server")
    public HmServer hmServer(@Qualifier("server_bootstrap") ServerBootstrap bootstrap, MProperties properties) {
        return new HmServer(
                bootstrap,
                new InetSocketAddress(properties.getIp(), properties.getPort())
        );
    }

    @Bean(name = "server_bootstrap")
    public ServerBootstrap bootstrap(@Qualifier("channel_initializer") ChannelInitializer<SocketChannel> initializer) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(2), new NioEventLoopGroup(20))
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(initializer);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 100);
        bootstrap.option(ChannelOption.TCP_NODELAY,true);
        return bootstrap;
    }

    @Bean(name = "channel_initializer")
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                ChannelPipeline cp = socketChannel.pipeline();
                cp.addLast("idleStateHandler", new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS));
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
            String mip = context.getEnvironment().getProperty("cornerstone.hm.master.ip");
            String sip = context.getEnvironment().getProperty("cornerstone.hm.slave.server");
            return StrUtil.isNotBlank(mip) && StrUtil.isBlank(sip);
        }

    }

}
