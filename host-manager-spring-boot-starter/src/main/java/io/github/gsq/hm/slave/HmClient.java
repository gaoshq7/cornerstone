package io.github.gsq.hm.slave;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.HmClient
 *
 * @author : gsq
 * @date : 2024-09-03 17:13
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class HmClient {

    private final String ip;

    private final Integer port;

    private final ChannelInitializer<SocketChannel> initializer;

    private Channel channel;

    public HmClient(String ip, Integer port, ChannelInitializer<SocketChannel> initializer) {
        this.ip = ip;
        this.port = port;
        this.initializer = initializer;
    }

    public void run() {
        doConnect(new NioEventLoopGroup());
    }

    public void stop() {
        if (channel != null) {
            this.channel.close();
            this.channel.parent().close();
        }
    }

    private void doConnect(EventLoopGroup group) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(this.initializer);
        bootstrap.remoteAddress(this.ip, this.port);
        GenericFutureListener<ChannelFuture> listener = cf -> {
            final EventLoop eventLoop = cf.channel().eventLoop();
            if (eventLoop.isShutdown()) {

            } else if (!cf.isSuccess()) {
                log.warn("连接服务器 {}:{} 失败，5s后重新尝试连接！", this.ip, this.port);
                eventLoop.schedule(() -> doConnect(eventLoop), 5, TimeUnit.SECONDS);
            } else {
                this.channel = cf.channel();
            }
        };
        ChannelFuture future = bootstrap.connect().addListener(listener);
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("重连同步关闭信道失败：{}", e.getMessage(), e);
        } finally {
            future.channel().closeFuture().removeListener(listener);
            future.channel().eventLoop().shutdownGracefully();
        }
    }

}
