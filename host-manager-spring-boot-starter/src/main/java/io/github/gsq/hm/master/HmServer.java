package io.github.gsq.hm.master;

import cn.hutool.core.collection.CollUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.HmServer
 *
 * @author : gsq
 * @date : 2024-09-03 17:12
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class HmServer {

    private final ChannelInitializer<SocketChannel> initializer;

    private final InetSocketAddress address;

    private final List<EventLoopGroup> groups;

    private Channel channel;

    public HmServer(ChannelInitializer<SocketChannel> initializer, InetSocketAddress address) {
        this.initializer = initializer;
        this.address = address;
        this.groups = CollUtil.newArrayList();
    }

    public synchronized void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup parent = new NioEventLoopGroup(2);
        EventLoopGroup child = new NioEventLoopGroup(20);
        bootstrap.group(parent, child)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(this.initializer);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 100);
        bootstrap.option(ChannelOption.TCP_NODELAY,true);
        this.groups.add(parent);
        this.groups.add(child);
        this.channel = bootstrap
                .bind(address)
                .sync()
                .channel()
                .closeFuture()
                .sync()
                .channel();
    }

    public boolean isActive() {
        return this.channel != null && this.channel.isActive();
    }

    public synchronized void stop() {
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        }
        if (!this.groups.isEmpty()) {
            for (EventLoopGroup group : this.groups) {
                group.shutdownGracefully();
            }
            this.groups.clear();
        }
    }

}
